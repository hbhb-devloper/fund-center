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
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isEmpty;

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
        // ??????????????????????????????????????????????????????????????????
        UserInfo userInfo = userApi.getUserInfoById(userId);
        Unit unit = unitApi.getUnitInfo(userInfo.getUnitId());
        if ("?????????".equals(unit.getUnitName())) {
            cond.setUnitId(UnitEnum.HANGZHOU.value());
        }

        // ????????????????????????
        List<Integer> unitIds = unitApi.getSubUnit(cond.getUnitId());
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        PageResult<InvoiceResVO> page = fundInvoiceMapper.selectPageByCond(cond, unitIds, request);

        // ????????????
        Map<String, String> stateMap = getStateMap();
        Map<String, String> businessMap = getBusinessMap();
        Map<String, String> contentMap = getInvoiceContentMap();

        Map<Boolean, String> isCancellationMap = new HashMap<>(5);
        isCancellationMap.put(true, "???");
        isCancellationMap.put(false, "???");

        page.getList().forEach(vo -> {
            vo.setInvoiceContent(contentMap.get(vo.getInvoiceContent()));
            vo.setBusiness(businessMap.get(vo.getBusiness()));
            vo.setStateLabel(stateMap.get(vo.getState().toString()));
            vo.setIsCancellationLabel(isCancellationMap.get(vo.getIsCancellation()));
        });
        return page;
    }

    @Override
    public InvoiceVO getFundInvoiceInfo(Long id) {
        FundInvoice fundInvoice = fundInvoiceMapper.single(id);
        InvoiceVO vo = BeanConverter.convert(fundInvoice, InvoiceVO.class);

        // ????????????
        Map<String, String> businessMap = getBusinessMap();
        Map<String, String> contentMap = getInvoiceContentMap();

        // ?????????????????????????????????
        vo.setArrearageMonth(DateUtil.dateToStringYmd(fundInvoice.getArrearageMonth()));
        vo.setArrearageMoney(fundInvoice.getArrearageMoney() == null ? "0.00"
                : NumberUtil.format(fundInvoice.getArrearageMoney().doubleValue()));
        vo.setAccountTime(DateUtil.dateToString(fundInvoice.getAccountTime()));
        vo.setAccountMoney(fundInvoice.getAccountMoney() == null ? "0.00"
                : NumberUtil.format(fundInvoice.getAccountMoney().doubleValue()));
        vo.setInvoiceContent(fundInvoice.getInvoiceContent() == null ? "" :
                fundInvoice.getInvoiceContent().toString());
        vo.setBusiness(fundInvoice.getBusiness().toString());
        vo.setInvoiceContentLabel(fundInvoice.getInvoiceContent() == null ? "" :
                contentMap.get(fundInvoice.getInvoiceContent().toString()));
        vo.setBusinessLabel(fundInvoice.getBusiness() == null ? "" :
                businessMap.get(fundInvoice.getBusiness().toString()));
        // ????????????
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
        FundInvoice invoice = buildInvoice(vo);
        // ????????????
        invoice.setClientManager(userInfo.getNickName());
        // ??????id
        invoice.setUnitId(userInfo.getUnitId());
        // ????????????????????????????????????
        invoice.setState(FlowState.NOT_APPROVED.value());
        fundInvoiceMapper.insertTemplate(invoice);
        // ??????
        if (!CollectionUtils.isEmpty(vo.getFiles())) {
            buildInvoiceFile(vo.getFiles(), userInfo.getNickName(), invoice.getId());
        }
    }

    @Override
    public void updateFundInvoice(InvoiceVO vo, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);
        String nickName = userInfo.getNickName();
        FundInvoice invoice = fundInvoiceMapper.single(vo.getId());
        // ???????????????????????????
        if (!nickName.equals(invoice.getClientManager())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        FundInvoice invoiceInfo = buildInvoice(vo);
        fundInvoiceMapper.updateTemplateById(invoiceInfo);
        // ????????????
        if (vo.getFiles() != null && vo.getFiles().size() != 0) {
            buildInvoiceFile(vo.getFiles(), nickName, invoice.getId());
        }
    }

    @Override
    public void updateFundInvoiceExtra(FundInvoiceExtraVO vo, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);
        FundInvoice fundInvoice = fundInvoiceMapper.single(vo.getId());
        FundInvoice invoice = new FundInvoice();
        invoice.setId(vo.getId());
        // ???????????????????????????????????????????????????
        if (fundInvoice.getState().equals(FlowState.APPROVED.value())) {
            if (!fundInvoice.getInvoiceUser().equals(userInfo.getNickName())) {
                throw new FundException(FundErrorCode.FUND_INVOICE_HAS_NO_WRITE_ACCESS);
            }
            // ????????????
            invoice.setAccountMoney(new BigDecimal(vo.getAccountMoney()));
            // ????????????
            invoice.setAccountTime(DateUtil.string2DateYMD(vo.getAccountTime()));
        }
        // ????????????????????????????????????
        else {
            // ???????????????
            invoice.setVersions(vo.getVersions());
            // ????????????
            invoice.setInvoiceNumber(vo.getInvoiceNumber());
            // ????????????
            invoice.setInvoiceCreateTime(new Date());
            // ?????????
            invoice.setInvoiceUser(userInfo.getNickName());
        }
        fundInvoiceMapper.updateTemplateById(invoice);
    }

    @Override
    public void discardFundInvoice(Long id, Boolean cancellation, Integer userId) {
        // ???????????????????????????????????????????????????
        List<String> roleNameList = flowRoleUserApi.getRoleNameByUserId(userId);
        if (!roleNameList.contains("???????????????")) {
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
        // ??????????????????
        if (!nickName.equals(invoice.getClientManager())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        // ????????????
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
        // ???????????????????????????????????????
        UserInfo userInfo = userApi.getUserInfoById(userId);
        FundInvoiceFile file = fundInvoiceFileMapper.single(fileId);
        if (file != null && !userInfo.getNickName().equals(file.getCreateBy())) {
            throw new FundException(FundErrorCode.FUND_INVOICE_PERMISSION_DENIED);
        }
        if (file == null) {
            // ????????????????????????????????????file????????????????????????
            fileApi.deleteFile(fileId);
        } else {
            // ?????????????????????????????????????????????????????????
            fundInvoiceFileMapper.deleteById(fileId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toApprove(FlowToApproveVO vo) {
        Long invoiceId = vo.getBusinessId();
        FundInvoice invoice = fundInvoiceMapper.single(invoiceId);
        // 1.???????????????????????????????????????????????????
        UserInfo user = userApi.getUserInfoById(vo.getUserId());
        if (!invoice.getClientManager().equals(user.getNickName())) {
            throw new FundFlowException(FundFlowErrorCode.LACK_OF_FLOW_ROLE);
        }
        // 1.1 ??????????????????????????????????????????
        // 2.????????????id
        Long flowId = getRelatedFlow(vo.getFlowTypeId(), invoice.getBusiness());
        // ????????????id????????????????????????
        List<FlowNodePropVO> flowProps = flowNodePropApi.getNodeProps(flowId);
        // 3.??????????????????????????????
        boolean hasAccess = hasAccess2Approve(flowProps, invoice.getUnitId(), vo.getUserId());
        if (!hasAccess) {
            throw new FundFlowException(FundFlowErrorCode.LACK_OF_FLOW_ROLE);
        }
        // 4.??????????????????
        syncBudgetProjectFlow(flowProps, invoice.getId(), vo.getUserId());
        // ??????????????????
        String inform = flowNodeNoticeApi.getInform(
                flowProps.get(0).getFlowNodeId(), FlowNodeNoticeState.DEFAULT_REMINDER.value());
        if (inform == null) {
            return;
        }
        // ????????????id??????????????????
        Flow flow = flowApi.getFlowById(flowId);
        // ??????????????????
        inform = inform.replace(FlowNodeNoticeTemp.TITLE.value(),
                invoice.getUnitNumber() + "_" + invoice.getUnitName() + "_" + flow.getFlowName());
        // ????????????????????????
        fundInvoiceNoticeMapper.insertTemplate(
                FundInvoiceNotice.builder()
                        .invoiceId(invoice.getId())
                        .receiver(vo.getUserId())
                        .promoter(vo.getUserId())
                        .content(inform)
                        .flowTypeId(vo.getFlowTypeId())
                        .createTime(new Date())
                        .build());

        //  6.????????????????????????
        fundInvoiceMapper.updateTemplateById(FundInvoice.builder()
                .id(invoiceId)
                .state(FlowState.APPROVING.value())
                .build());
    }

    private void syncBudgetProjectFlow(List<FlowNodePropVO> flowProps, Long invoiceId, Integer userId) {

        // ???????????????????????????list
        List<FundInvoiceFlow> invoiceFlowList = new ArrayList<>();
        // ?????????????????????????????????
        for (FlowNodePropVO flowPropVO : flowProps) {
            if (flowPropVO.getIsJoin() == null || flowPropVO.getControlAccess() == null) {
                throw new FundFlowException(FundFlowErrorCode.LACK_OF_NODE_PROP);
            }
        }
        // ???????????????????????????????????????????????????????????????????????????
        if (flowProps.get(0).getUserId() == null) {
            flowProps.get(0).setUserId(userId);
        }
        // ?????????????????????????????????
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
        // ?????????????????????
        FlowNodePropVO firstNodeProp = flowProps.get(0);
        // ????????????????????????
        // ???????????????????????????????????????????????????????????????????????????
        if (firstNodeProp.getUserId() != null) {
            return firstNodeProp.getUserId().equals(userId);
        }
        // ????????????????????????????????????????????????????????????
        else {
            // ??????????????????????????????
            if (UnitEnum.isHangzhou(firstNodeProp.getUnitId()) || firstNodeProp.getUnitId().equals(0)) {
                return flowRoleIds.contains(firstNodeProp.getFlowRoleId());
            }
            // ???????????????????????????
            else if (UnitEnum.isBenbu(firstNodeProp.getUnitId())) {
                List<Integer> unitIds = unitApi.getSubUnit(UnitEnum.BENBU.value());
                return unitIds.contains(unitId) && flowRoleIds
                        .contains(firstNodeProp.getFlowRoleId());
            }
            // ???????????????????????????
            else {
                // ?????????????????????????????????????????????????????????????????????
                return unitId.equals(firstNodeProp.getUnitId())
                        && flowRoleIds.contains(firstNodeProp.getFlowRoleId());
            }
        }
    }

    private Long getRelatedFlow(Long flowTypeId, Integer business) {
        // ?????????????????? => ??????id
        Map<Long, Long> flowMap = new HashMap<>();
        List<Flow> flowList = flowApi.getFlowsByTypeId(flowTypeId);
        // ?????????????????????????????????????????????????????????
        if (flowList.size() == 0) {
            throw new FundFlowException(FundFlowErrorCode.NOT_EXIST_FLOW);
        } else if (flowList.size() > 2) {
            throw new FundFlowException(FundFlowErrorCode.EXCEED_LIMIT_FLOW);
        }
        flowList.forEach(flow -> flowMap.put(flowNodeApi.getNodeNum(flow.getId()), flow.getId()));
        // ???????????????????????????4??????????????? ??????????????????????????????????????????????????????????????????
        Long flowId;
        if (business != 20) {
            flowId = flowMap.get(4L);
        } else {
            flowId = flowMap.get(2L);
        }
        if (flowId == null) {
            throw new FundFlowException(FundFlowErrorCode.LACK_OF_FLOW);
        }
        // ????????????????????????????????????????????????????????????????????????
        return flowId;
    }

    /**
     * ????????????????????????
     */
    private Map<String, String> getStateMap() {
        List<DictVO> stateList = dictApiExp.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_STATUS.value());
        return stateList.stream().collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
    }

    /**
     * ????????????????????????
     */
    private Map<String, String> getBusinessMap() {
        List<DictVO> businessList = dictApiExp.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_BUSINESS.value());
        return businessList.stream().collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
    }

    /**
     * ????????????????????????
     */
    private Map<String, String> getInvoiceContentMap() {
        List<DictVO> businessList = dictApiExp.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_CONTENT.value());
        return businessList.stream().collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
    }

    /**
     * ??????????????????
     */
    private FundInvoice buildInvoice(InvoiceVO vo) {
        FundInvoice invoice = BeanConverter.convert(vo, FundInvoice.class);
        if (!isEmpty(vo.getInvoiceContent())) {
            invoice.setInvoiceContent(Integer.parseInt(vo.getInvoiceContent()));
        }
        if (!isEmpty(vo.getBusiness())) {
            invoice.setBusiness(Integer.valueOf(vo.getBusiness()));
        }
        invoice.setInvoiceAmount(vo.getInvoiceAmount());
        // ?????????????????????????????????????????????????????????
        if (invoice.getBusiness() == 20) {
            // ????????????
            invoice.setArrearageMoney(new BigDecimal(vo.getArrearageMoney()));
            // ????????????
            invoice.setArrearageMonth(DateUtil.string2DateYMD(vo.getArrearageMonth()));
        }
        invoice.setCreateTime(new Date());
        return invoice;
    }

    /**
     * ????????????????????????
     */
    private void buildInvoiceFile(List<InvoiceFileVO> files, String nickName, Long invoiceId) {
        List<FundInvoiceFile> insertFiles = new ArrayList<>();
        Date now = new Date();

        // ????????????????????????????????????
        List<FundInvoiceFile> invoiceFiles = fundInvoiceFileMapper.createLambdaQuery()
                .andEq(FundInvoiceFile::getInvoiceId, invoiceId)
                .select(FundInvoiceFile::getId);
        // ??????????????????????????????
        if (!CollectionUtils.isEmpty(invoiceFiles)) {
            return;
        }
        List<Long> fileIds = invoiceFiles.stream().map(FundInvoiceFile::getId).collect(Collectors.toList());
        files.forEach(vo -> {
            // ????????????????????????
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
