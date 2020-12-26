package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.core.utils.DateUtil;
import com.hbhb.core.utils.NumberUtil;
import com.hbhb.cw.flowcenter.enums.FlowNodeNoticeState;
import com.hbhb.cw.flowcenter.enums.FlowNodeNoticeTemp;
import com.hbhb.cw.flowcenter.enums.FlowOperationType;
import com.hbhb.cw.flowcenter.enums.FlowState;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;
import com.hbhb.cw.flowcenter.vo.FlowToApproveVO;
import com.hbhb.cw.fundcenter.enums.code.FundErrorCode;
import com.hbhb.cw.fundcenter.enums.code.FundFlowErrorCode;
import com.hbhb.cw.fundcenter.exception.FundException;
import com.hbhb.cw.fundcenter.exception.FundFlowException;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceFileMapper;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceFlowMapper;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceMapper;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceNoticeMapper;
import com.hbhb.cw.fundcenter.model.FundInvoice;
import com.hbhb.cw.fundcenter.model.FundInvoiceFile;
import com.hbhb.cw.fundcenter.model.FundInvoiceFlow;
import com.hbhb.cw.fundcenter.model.FundInvoiceNotice;
import com.hbhb.cw.fundcenter.rpc.DictApiExp;
import com.hbhb.cw.fundcenter.rpc.FileApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowNodeApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowNodeNoticeApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowNodePropApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowRoleUserApiExp;
import com.hbhb.cw.fundcenter.rpc.UnitApiExp;
import com.hbhb.cw.fundcenter.rpc.UserApiExp;
import com.hbhb.cw.fundcenter.service.FundInvoiceService;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceExtraVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceExportVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceFileVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceReqVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceResVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceVO;
import com.hbhb.cw.systemcenter.enums.DictCode;
import com.hbhb.cw.systemcenter.enums.TypeCode;
import com.hbhb.cw.systemcenter.enums.UnitEnum;
import com.hbhb.cw.systemcenter.model.SysFile;
import com.hbhb.cw.systemcenter.model.Unit;
import com.hbhb.cw.systemcenter.vo.DictVO;
import com.hbhb.cw.systemcenter.vo.UserInfo;

import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wxg
 * @since 2020-09-03
 */
@Service
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class FundInvoiceImpl implements FundInvoiceService {

    @Resource
    private FundInvoiceMapper fundInvoiceMapper;
    @Resource
    private FundInvoiceFlowMapper fundInvoiceFlowMapper;
    @Resource
    private FundInvoiceFileMapper fundInvoiceFileMapper;
    @Resource
    private FundInvoiceNoticeMapper fundInvoiceNoticeMapper;
    @Resource
    private UserApiExp userApi;
    @Resource
    private UnitApiExp unitApi;
    @Resource
    private DictApiExp dictApiExp;
    @Resource
    private FileApiExp fileApi;
    @Resource
    private FlowApiExp flowApi;
    @Resource
    private FlowNodeApiExp flowNodeApi;
    @Resource
    private FlowNodePropApiExp flowNodePropApi;
    @Resource
    private FlowNodeNoticeApiExp flowNodeNoticeApi;
    @Resource
    private FlowRoleUserApiExp flowRoleUserApi;

    @Override
    public PageResult<InvoiceResVO> pageFundInvoice(Integer pageNum, Integer pageSize,
                                                    Integer userId, InvoiceReqVO cond) {
        // 若登录用户为财务部用户，则可以查看全杭州数据
        UserInfo userInfo = userApi.getUserInfoById(userId);
        Unit unit = unitApi.getUnitInfo(userInfo.getUnitId());
        if ("财务部".equals(unit.getUnitName())) {
            cond.setUnitId(UnitEnum.HANGZHOU.value());
        }

        // 获取所有下属单位
        List<Integer> unitIds = unitApi.getSubUnit(cond.getUnitId());
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        PageResult<InvoiceResVO> page = fundInvoiceMapper.selectPageByCond(cond, unitIds, request);

        // 获取字典
        Map<String, String> stateMap = getStateMap();
        Map<String, String> businessMap = getBusinessMap();
        Map<String, String> contentMap = getInvoiceContentMap();

        page.getList().forEach(vo -> {
            vo.setInvoiceContent(contentMap.get(vo.getInvoiceContent()));
            vo.setBusiness(businessMap.get(vo.getBusiness()));
            vo.setState(stateMap.get(vo.getState()));
        });
        return page;
    }

    @Override
    public InvoiceVO getFundInvoiceInfo(Long id) {
        FundInvoice fundInvoice = fundInvoiceMapper.single(id);
        InvoiceVO vo = BeanConverter.convert(fundInvoice, InvoiceVO.class);

        // 获取字典
        Map<String, String> businessMap = getBusinessMap();
        Map<String, String> contentMap = getInvoiceContentMap();

        // 金额日期字段格式化处理
        vo.setInvoiceAmount(fundInvoice.getInvoiceAmount() == null ? "0.00"
                : NumberUtil.format(fundInvoice.getInvoiceAmount().doubleValue()));
        vo.setArrearageMonth(DateUtil.dateToStringYmd(fundInvoice.getArrearageMonth()));
        vo.setArrearageMoney(fundInvoice.getArrearageMoney() == null ? "0.00"
                : NumberUtil.format(fundInvoice.getArrearageMoney().doubleValue()));
        vo.setAccountTime(DateUtil.dateToString(fundInvoice.getAccountTime()));
        vo.setAccountMoney(fundInvoice.getAccountMoney() == null ? "0.00"
                : NumberUtil.format(fundInvoice.getAccountMoney().doubleValue()));
        vo.setInvoiceContent(fundInvoice.getInvoiceContent() == null ? ""
                : contentMap.get(fundInvoice.getInvoiceContent().toString()));
        vo.setBusiness(businessMap.get(fundInvoice.getBusiness().toString()));

        // 获取附件
        List<InvoiceFileVO> files = new ArrayList<>();
        List<FundInvoiceFile> invoiceFiles = fundInvoiceFileMapper.createLambdaQuery()
                .andEq(FundInvoiceFile::getInvoiceId, id)
                .select();
        invoiceFiles.forEach(invoiceFile -> {
            SysFile sysFile = fileApi.getFileInfo(Math.toIntExact(invoiceFile.getFileId()));
            files.add(InvoiceFileVO.builder()
                    .id(invoiceFile.getId())
                    .fileName(sysFile.getFileName())
                    .author(invoiceFile.getCreateBy())
                    .uploadTime(invoiceFile.getCreateTime())
                    .fileSize(sysFile.getFileSize())
                    .filePath(sysFile.getFilePath())
                    .build());
        });
        vo.setFiles(files);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFundInvoice(InvoiceVO vo, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);
        FundInvoice invoice = buildInvoice(vo, userInfo);
        // 客户经理
        invoice.setClientManager(userInfo.getNickName());
        // 单位id
        invoice.setUnitId(userInfo.getUnitId());
        // 流程状态默认为未开始审批
        invoice.setState(FlowState.NOT_APPROVED.value());
        fundInvoiceMapper.insertTemplate(invoice);
        // 附件
        if (!CollectionUtils.isEmpty(vo.getFiles())) {
            buildInvoiceFile(vo.getFiles(), userInfo.getNickName(), invoice.getId());
        }
    }

    @Override
    public void updateFundInvoice(InvoiceVO vo, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);
        String nickName = userInfo.getNickName();
        FundInvoice invoice = fundInvoiceMapper.single(vo.getId());
        // 判断用户身份
        if (!nickName.equals(invoice.getClientManager()) || !nickName.equals(invoice.getInvoiceUser())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        FundInvoice invoiceInfo = buildInvoice(vo, userInfo);
        fundInvoiceMapper.updateTemplateById(invoiceInfo);
        // 修改附件
        if (vo.getFiles() != null && vo.getFiles().size() != 0) {
            buildInvoiceFile(vo.getFiles(), nickName, invoice.getId());
        }
    }

    @Override
    public void updateFundInvoiceExtra(FundInvoiceExtraVO vo, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);
        FundInvoice fundInvoice = fundInvoiceMapper.single(vo.getId());
        if (!fundInvoice.getInvoiceUser().equals(userInfo.getNickName())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_HAS_NO_WRITE_ACCESS);
        }

        FundInvoice invoice = new FundInvoice();
        invoice.setId(vo.getId());
        // 如果是流程已完成，则补充该两个字段
        if (fundInvoice.getState().equals(FlowState.APPROVED.value())) {
            // 到账金额
            invoice.setAccountMoney(new BigDecimal(vo.getAccountMoney()));
            // 到账时间
            invoice.setAccountTime(DateUtil.string2DateYMD(vo.getAccountTime()));
        }
        // 如果是收账员节点审批，则
        else {
            // 发票版本号
            invoice.setVersions(vo.getVersions());
            // 发票编号
            invoice.setInvoiceNumber(vo.getInvoiceNumber());
            // 开票时间
            invoice.setInvoiceCreateTime(new Date());
            // 开票人
            invoice.setInvoiceUser(userInfo.getNickName());
        }
        fundInvoiceMapper.updateTemplateById(invoice);
    }

    @Override
    public void discardFundInvoice(Long id, Boolean cancellation, Integer userId) {
        // 只有“发票作废员”才有权限作废发票
        List<String> roleNameList = flowRoleUserApi.getRoleNameByUserId(userId);
        if (!roleNameList.contains("发票作废员")) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        fundInvoiceMapper.updateTemplateById(FundInvoice.builder()
                .id(id)
                .isCancellation(cancellation)
                .build());
    }

    @Override
    public void deleteFundInvoice(Long id, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);
        String nickName = userInfo.getNickName();
        FundInvoice invoice = fundInvoiceMapper.single(id);
        // 判断用户身份
        if (!nickName.equals(invoice.getClientManager()) || !nickName.equals(invoice.getInvoiceUser())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        // 逻辑删除
        fundInvoiceMapper.updateTemplateById(FundInvoice.builder().id(id).deleteFlag(0).build());
    }

    @Override
    public List<InvoiceExportVO> getExportList(InvoiceReqVO cond, Integer userId) {
        PageResult<InvoiceResVO> page = this.pageFundInvoice(1, Integer.MAX_VALUE, userId, cond);
        return Optional.ofNullable(page.getList())
                .orElse(new ArrayList<>())
                .stream()
                .map(vo -> BeanConverter.convert(vo, InvoiceExportVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFundInvoiceFile(Long fileId, Integer userId) {
        // 判断用户是否有删除附件权限
        UserInfo userInfo = userApi.getUserInfoById(userId);
        FundInvoiceFile file = fundInvoiceFileMapper.single(fileId);
        if (file != null && !userInfo.getNickName().equals(file.getCreateBy())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        if (file == null) {
            // 如果附件不存在，则将系统file表中对应数据清除
            fileApi.deleteFile(fileId);
        } else {
            // 如果附件存在，且当前用户有权限，则删除
            fundInvoiceFileMapper.deleteById(fileId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toApprove(FlowToApproveVO vo) {
        Long invoiceId = vo.getBusinessId();
        FundInvoice invoice = fundInvoiceMapper.single(invoiceId);
        // 1.判断登录用户是否与发票客户经理一致
        UserInfo user = userApi.getUserInfoById(vo.getUserId());
        if (!user.getNickName().equals(invoice.getClientManager())) {
            throw new FundFlowException(FundFlowErrorCode.LACK_OF_FLOW_ROLE);
        }
        // 1.1 客户经理判断发票是否符合要求
        // 2.获取流程id
        Long flowId = getRelatedFlow(vo.getFlowTypeId(), invoice.getBusiness());
        // 通过流程id得到流程节点属性
        List<FlowNodePropVO> flowProps = flowNodePropApi.getNodeProps(flowId);
        // 3.校验用户发起审批权限
        boolean hasAccess = hasAccess2Approve(flowProps, invoice.getUnitId(), vo.getUserId());
        if (!hasAccess) {
            throw new FundFlowException(FundFlowErrorCode.LACK_OF_FLOW_ROLE);
        }
        // 4.同步节点属性
        syncBudgetProjectFlow(flowProps, invoice.getId(), vo.getUserId());
        // 得到推送模板
        String inform = flowNodeNoticeApi.getInform(
                flowProps.get(0).getFlowNodeId(), FlowNodeNoticeState.DEFAULT_REMINDER.value());
        if (inform == null) {
            return;
        }
        // 跟据流程id获取流程名称
        Flow flow = flowApi.getFlowById(flowId);
        // 修改推送模板
        inform = inform.replace(FlowNodeNoticeTemp.TITLE.value(),
                invoice.getUnitNumber() + "_" + invoice.getUnitName() + "_" + flow.getFlowName());
        // 推送消息给发起人
        fundInvoiceNoticeMapper.insertTemplate(
                FundInvoiceNotice.builder()
                        .invoiceId(invoice.getId())
                        .receiver(vo.getUserId())
                        .promoter(vo.getUserId())
                        .content(inform)
                        .flowTypeId(vo.getFlowTypeId())
                        .createTime(new Date())
                        .build());

        //  6.更改发票流程状态
        fundInvoiceMapper.updateTemplateById(FundInvoice.builder()
                .id(invoiceId)
                .state(FlowState.APPROVING.value())
                .build());
    }

    private void syncBudgetProjectFlow(List<FlowNodePropVO> flowProps, Long invoiceId, Integer userId) {

        // 用来存储同步节点的list
        List<FundInvoiceFlow> invoiceFlowList = new ArrayList<>();
        // 判断节点是否有保存属性
        for (FlowNodePropVO flowPropVO : flowProps) {
            if (flowPropVO.getIsJoin() == null || flowPropVO.getControlAccess() == null) {
                throw new FundFlowException(FundFlowErrorCode.LACK_OF_NODE_PROP);
            }
        }
        // 判断第一个节点是否有默认用户，如果没有则为当前用户
        if (flowProps.get(0).getUserId() == null) {
            flowProps.get(0).setUserId(userId);
        }
        // 先清空节点，再同步节点
        fundInvoiceFlowMapper.createLambdaQuery()
                .andEq(FundInvoiceFlow::getInvoiceId, invoiceId)
                .delete();
        for (FlowNodePropVO flowPropVO : flowProps) {
            invoiceFlowList.add(FundInvoiceFlow.builder()
                    .flowNodeId(flowPropVO.getFlowNodeId())
                    .invoiceId(invoiceId)
                    .userId(flowPropVO.getUserId())
                    .flowRoleId(flowPropVO.getFlowRoleId())
                    .roleDesc(flowPropVO.getRoleDesc())
                    .controlAccess(flowPropVO.getControlAccess())
                    .isJoin(flowPropVO.getIsJoin())
                    .assigner(flowPropVO.getAssigner())
                    .operation(FlowOperationType.UN_EXECUTED.value())
                    .build());
        }
        fundInvoiceFlowMapper.insertBatch(invoiceFlowList);
    }

    private boolean hasAccess2Approve(List<FlowNodePropVO> flowProps, Integer unitId, Integer userId) {
        List<Long> flowRoleIds = flowRoleUserApi.getRoleIdByUserId(userId);
        // 第一个节点属性
        FlowNodePropVO firstNodeProp = flowProps.get(0);
        // 判断是有默认用户
        // 如果设定了默认用户，且为当前登录用户，则有发起权限
        if (firstNodeProp.getUserId() != null) {
            return firstNodeProp.getUserId().equals(userId);
        }
        // 如果没有设定默认用户，则通过流程角色判断
        else {
            // 如果角色范围为全杭州
            if (UnitEnum.isHangzhou(firstNodeProp.getUnitId()) || firstNodeProp.getUnitId().equals(0)) {
                return flowRoleIds.contains(firstNodeProp.getFlowRoleId());
            }
            // 如果角色范围为本部
            else if (UnitEnum.isBenbu(firstNodeProp.getUnitId())) {
                List<Integer> unitIds = unitApi.getSubUnit(UnitEnum.BENBU.value());
                return unitIds.contains(unitId) && flowRoleIds
                        .contains(firstNodeProp.getFlowRoleId());
            }
            // 如果为确定某个单位
            else {
                // 必须单位和流程角色都匹配，才可判定为有发起权限
                return unitId.equals(firstNodeProp.getUnitId())
                        && flowRoleIds.contains(firstNodeProp.getFlowRoleId());
            }
        }
    }

    private Long getRelatedFlow(Long flowTypeId, Integer business) {
        // 流程节点数量 => 流程id
        Map<Long, Long> flowMap = new HashMap<>();
        List<Flow> flowList = flowApi.getFlowsByTypeId(flowTypeId);
        // 流程有效性校验（发票预开流程存在两条）
        if (flowList.size() == 0) {
            throw new FundFlowException(FundFlowErrorCode.NOT_EXIST_FLOW);
        } else if (flowList.size() > 2) {
            throw new FundFlowException(FundFlowErrorCode.EXCEED_LIMIT_FLOW);
        }
        flowList.forEach(flow -> flowMap.put(flowNodeApi.getNodeNum(flow.getId()), flow.getId()));
        // 预开发票流程默认为4个节点流程 若办理业务为欠费缴纳类型则走另一条两节点流程
        Long flowId;
        if (business != 20) {
            flowId = flowMap.get(4L);
        } else {
            flowId = flowMap.get(2L);
        }
        if (flowId == null) {
            throw new FundFlowException(FundFlowErrorCode.LACK_OF_FLOW);
        }
        // 校验流程是否匹配，如果没有匹配的流程，则抛出提示
        return flowId;
    }

    /**
     * 获取流程状态字典
     */
    private Map<String, String> getStateMap() {
        List<DictVO> stateList = dictApiExp.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_STATUS.value());
        return stateList.stream().collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
    }

    /**
     * 获取办理业务字典
     */
    private Map<String, String> getBusinessMap() {
        List<DictVO> businessList = dictApiExp.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_BUSINESS.value());
        return businessList.stream().collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
    }

    /**
     * 获取开票内容字典
     */
    private Map<String, String> getInvoiceContentMap() {
        List<DictVO> businessList = dictApiExp.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_CONTENT.value());
        return businessList.stream().collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
    }

    /**
     * 组装发票实体
     */
    private FundInvoice buildInvoice(InvoiceVO vo, UserInfo userInfo) {
        if (!userInfo.getNickName().equals(vo.getInvoiceUser())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_USER_INCORRECT);
        }
        if (!StringUtils.isEmpty(vo.getAccountMoney())
                || !StringUtils.isEmpty(vo.getAccountTime())
                || !StringUtils.isEmpty(vo.getInvoiceNumber())
                || !StringUtils.isEmpty(vo.getVersions())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_HAS_NO_WRITE_ACCESS);
        }

        FundInvoice invoice = BeanConverter.convert(vo, FundInvoice.class);
        invoice.setInvoiceAmount(new BigDecimal(vo.getInvoiceAmount()));
        invoice.setBusiness(Integer.valueOf(vo.getBusiness()));
        // 办理业务类型为欠费缴纳时，两个字段必需
        if (invoice.getBusiness() == 20) {
            // 欠费金额
            invoice.setArrearageMoney(new BigDecimal(vo.getArrearageMoney()));
            // 欠费时间
            invoice.setArrearageMonth(DateUtil.string2DateYMD(vo.getArrearageMonth()));
        }
        invoice.setCreateTime(new Date());
        return invoice;
    }

    /**
     * 组装发票附件实体
     */
    private void buildInvoiceFile(List<InvoiceFileVO> files, String nickName, Long invoiceId) {
        List<FundInvoiceFile> insertFiles = new ArrayList<>();
        Date now = new Date();

        // 判断该发票下是否存在附件
        List<FundInvoiceFile> invoiceFiles = fundInvoiceFileMapper.createLambdaQuery()
                .andEq(FundInvoiceFile::getInvoiceId, invoiceId)
                .select(FundInvoiceFile::getId);
        // 如果不存在，则不用新增
        if (CollectionUtils.isEmpty(invoiceFiles)) {
            return;
        }
        List<Long> fileIds = invoiceFiles.stream().map(FundInvoiceFile::getId).collect(Collectors.toList());
        files.forEach(vo -> {
            // 如果存在，则新增
            if (!fileIds.contains(vo.getId())) {
                insertFiles.add(FundInvoiceFile.builder()
                        .invoiceId(invoiceId)
                        .required(true)
                        .createTime(now)
                        .createBy(nickName)
                        .fileId(vo.getFileId())
                        .build());
            }
        });
        fundInvoiceFileMapper.insertBatch(insertFiles);
    }
}
