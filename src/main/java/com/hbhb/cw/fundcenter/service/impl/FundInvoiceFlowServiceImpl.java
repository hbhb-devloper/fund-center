package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.core.bean.BeanConverter;
import com.hbhb.core.utils.DateUtil;
import com.hbhb.cw.flowcenter.enums.FlowNodeNoticeState;
import com.hbhb.cw.flowcenter.enums.FlowNodeNoticeTemp;
import com.hbhb.cw.flowcenter.enums.FlowOperationType;
import com.hbhb.cw.flowcenter.enums.FlowState;
import com.hbhb.cw.flowcenter.vo.FlowApproveVO;
import com.hbhb.cw.flowcenter.vo.FlowWrapperVO;
import com.hbhb.cw.flowcenter.vo.NodeApproverReqVO;
import com.hbhb.cw.flowcenter.vo.NodeApproverVO;
import com.hbhb.cw.flowcenter.vo.NodeInfoVO;
import com.hbhb.cw.flowcenter.vo.NodeOperationReqVO;
import com.hbhb.cw.flowcenter.vo.NodeOperationVO;
import com.hbhb.cw.flowcenter.vo.NodeSuggestionVO;
import com.hbhb.cw.fundcenter.enums.InvoiceNoticeState;
import com.hbhb.cw.fundcenter.enums.NodeAccessFiled;
import com.hbhb.cw.fundcenter.enums.code.FundErrorCode;
import com.hbhb.cw.fundcenter.enums.code.FundFlowErrorCode;
import com.hbhb.cw.fundcenter.exception.FundException;
import com.hbhb.cw.fundcenter.exception.FundFlowException;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceFlowMapper;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceMapper;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceNoticeMapper;
import com.hbhb.cw.fundcenter.model.FundInvoice;
import com.hbhb.cw.fundcenter.model.FundInvoiceFlow;
import com.hbhb.cw.fundcenter.model.FundInvoiceNotice;
import com.hbhb.cw.fundcenter.rpc.FlowApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowNodeNoticeApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowRoleApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowRoleUserApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowTypeApiExp;
import com.hbhb.cw.fundcenter.rpc.UserApiExp;
import com.hbhb.cw.fundcenter.service.FundInvoiceFlowService;
import com.hbhb.cw.fundcenter.service.MailService;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceFlowVO;
import com.hbhb.cw.systemcenter.vo.UserInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wxg
 * @since 2020-09-08
 */
@Slf4j
@Service
public class FundInvoiceFlowServiceImpl implements FundInvoiceFlowService {

    @Resource
    private FundInvoiceMapper fundInvoiceMapper;
    @Resource
    private FundInvoiceFlowMapper fundInvoiceFlowMapper;
    @Resource
    private FundInvoiceNoticeMapper fundInvoiceNoticeMapper;
    @Resource
    private MailService mailService;
    @Resource
    private UserApiExp userApi;
    @Resource
    private FlowApiExp flowApi;
    @Resource
    private FlowRoleApiExp flowRoleApi;
    @Resource
    private FlowTypeApiExp flowTypeApi;
    @Resource
    private FlowNodeNoticeApiExp flowNodeNoticeApi;
    @Resource
    private FlowRoleUserApiExp flowRoleUserApi;

    @Value("${mail.enable}")
    private Boolean mailEnable;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(FlowApproveVO approveVO, Integer userId) {
        // 查询当前节点信息
        FundInvoiceFlow currentNode = fundInvoiceFlowMapper.single(approveVO.getId());
        // 校验登录用户是否为审批人
        if (!userId.equals(currentNode.getUserId())) {
            throw new FundFlowException(FundFlowErrorCode.LOCK_OF_APPROVAL_ROLE);
        }

        Date now = new Date();
        UserInfo userInfo = userApi.getUserInfoById(userId);
        // 当前节点id
        String currentNodeId = currentNode.getFlowNodeId();
        // 所有节点对应的审批人
        List<NodeApproverReqVO> approvers = approveVO.getApprovers();
        // 审批人map（节点id <=> 审批人id）
        Map<String, Integer> approverMap = approvers.stream()
                .collect(Collectors.toMap(NodeApproverReqVO::getFlowNodeId, NodeApproverReqVO::getUserId));
        // 所有节点id
        List<String> nodeIds = approvers.stream()
                .map(NodeApproverReqVO::getFlowNodeId).collect(Collectors.toList());

        // 发票id
        Long invoiceId = currentNode.getInvoiceId();
        FundInvoice invoice = fundInvoiceMapper.single(invoiceId);
        // 流程名称
        String flowName = flowApi.getNameByNodeId(nodeIds.get(0));
        // 流程类型
        Long flowTypeId = flowTypeApi.getTypeIdByNode(nodeIds.get(0));
        // 提醒标题 = 发票单位编号_发票单位名称_流程名称
        String title = invoice.getUnitNumber() + "_" + invoice.getUnitName() + "_" + flowName;
        // 节点操作
        Integer operation = null;
        // 流程状态
        Integer flowState = null;

        // 更新当前节点之前的所有节点提醒状态为已读
        fundInvoiceNoticeMapper.updateTemplateById(FundInvoiceNotice.builder()
                .id(invoiceId)
                .state(InvoiceNoticeState.READ.value())
                .build());

        // 同意
        if (FlowOperationType.AGREE.value().equals(approveVO.getOperation())) {
            operation = FlowOperationType.AGREE.value();
            // 1.判断当前是否为最后一个节点
            // 如果是最后一个节点，则需要更新流程状态
            if (isLastNode(currentNodeId, nodeIds)) {
                flowState = FlowState.APPROVED.value();
                // 最后一个节点为收账员，需填写部分必填业务字段
                if (!checkContent(invoiceId)) {
                    throw new FundException(FundErrorCode.FUND_INVOICE_NOT_INPUT_ERROR);
                }
            }
            // 如果不是最后一个节点
            else {
                // 当前用户的所有流程角色
                List<Long> flowRoleIds = flowRoleUserApi.getRoleIdByUserId(userId);
                // 下一个节点的审批人
                Integer next = approverMap.get(getNextNode(currentNodeId, nodeIds));
                // 2.判断当前用户是否为分配者
                // 2-1.如果是分配者
                if (flowRoleIds.contains(currentNode.getAssigner())) {
                    // 校验是否所有节点的审批人已指定
                    if (approvers.stream().anyMatch(vo -> vo.getUserId() == null)) {
                        throw new FundFlowException(FundFlowErrorCode.NOT_ALL_APPROVERS_ASSIGNED);
                    }
                    // 更新各节点审批人
                    fundInvoiceFlowMapper.updateBatchTempById(approvers.stream().map(vo ->
                            FundInvoiceFlow.builder()
                                    .id(vo.getId())
                                    .userId(vo.getUserId())
                                    .build()).collect(Collectors.toList()));
                }
                // 2-2.如果不是分配者
                else {
                    // 校验下一个节点的审批人是否已指定
                    if (next == null) {
                        throw new FundFlowException(FundFlowErrorCode.NOT_ALL_APPROVERS_ASSIGNED);
                    }
                }

                // 3.保存提醒消息
                // 3-1.提醒下一个节点的审批人
                String inform = flowNodeNoticeApi.getInform(currentNodeId, FlowNodeNoticeState.DEFAULT_REMINDER.value());
                this.saveNotice(invoiceId, next, userId,
                        inform.replace(FlowNodeNoticeTemp.TITLE.value(), title), flowTypeId, now);
                // 3-2.邮件推送
                if (mailEnable) {
                    UserInfo nextUser = userApi.getUserInfoById(next);
                    mailService.postMail(nextUser.getEmail(), nextUser.getNickName(), title);
                }
            }
            // 3-3.提醒发起人
            String inform = flowNodeNoticeApi.getInform(currentNodeId, FlowNodeNoticeState.COMPLETE_REMINDER.value());
            String content = inform.replace(FlowNodeNoticeTemp.TITLE.value(), title)
                    .replace(FlowNodeNoticeTemp.APPROVE.value(), userInfo.getNickName());
            this.saveNotice(invoiceId, approvers.get(0).getUserId(), userId, content, flowTypeId, now);
        }
        // 拒绝
        else if (approveVO.getOperation().equals(FlowOperationType.REJECT.value())) {
            operation = FlowOperationType.REJECT.value();
            flowState = FlowState.APPROVE_REJECTED.value();
            // 提醒发起人
            String inform = flowNodeNoticeApi.getInform(currentNodeId, FlowNodeNoticeState.REJECT_REMINDER.value());
            String content = inform.replace(FlowNodeNoticeTemp.TITLE.value(), title)
                    .replace(FlowNodeNoticeTemp.APPROVE.value(), userInfo.getNickName())
                    .replace(FlowNodeNoticeTemp.CAUSE.value(), approveVO.getSuggestion());
            this.saveNotice(invoiceId, approvers.get(0).getUserId(), userId, content, flowTypeId, now);
        }

        // 更新节点信息
        fundInvoiceFlowMapper.updateTemplateById(FundInvoiceFlow.builder()
                .operation(operation)
                .suggestion(approveVO.getSuggestion())
                .updateTime(now)
                .id(approveVO.getId())
                .build());

        // 更新流程状态
        if (flowState != null) {
            fundInvoiceMapper.updateTemplateById(
                    FundInvoice.builder().id(invoiceId).state(flowState).build());
        }
    }

    private void saveNotice(Long invoiceId, Integer receiver, Integer userId, String content,
                            Long flowTypeId, Date now) {
        fundInvoiceNoticeMapper.insertTemplate(FundInvoiceNotice.builder()
                .invoiceId(invoiceId)
                .receiver(receiver)
                .promoter(userId)
                .content(content)
                .flowTypeId(flowTypeId)
                .createTime(now)
                .build());
    }

    /**
     * 判断当前节点是否为流程中的最后一个节点
     */
    private boolean isLastNode(String currentNodeId, List<String> nodeIds) {
        return currentNodeId.equals(nodeIds.get(nodeIds.size() - 1));
    }

    /**
     * 获取当前节点的下一个节点
     */
    private String getNextNode(String currentNodeId, List<String> nodeIds) {
        if (!nodeIds.contains(currentNodeId) || isLastNode(currentNodeId, nodeIds)) {
            return null;
        }
        return nodeIds.get(nodeIds.indexOf(currentNodeId) + 1);
    }

    /**
     * 判断收账员是否已填写发票编号，发票版本号
     */
    private Boolean checkContent(Long invoiceId) {
        FundInvoice invoice = fundInvoiceMapper.single(invoiceId);
        return invoice.getVersions() != null && invoice.getInvoiceNumber() != null;
    }

    @Override
    public FlowWrapperVO getFlowInfo(Long invoiceId, Integer userId) {
        FlowWrapperVO wrapper = new FlowWrapperVO();
        List<NodeInfoVO> nodes = new ArrayList<>();
        UserInfo userInfo = userApi.getUserInfoById(userId);

        // 查询流程的所有节点
        List<FundInvoiceFlowVO> flowNodes = this.getFlowNodes(invoiceId);
        if (CollectionUtils.isEmpty(flowNodes)) {
            return new FlowWrapperVO();
        }
        Map<String, FundInvoiceFlowVO> flowNodeMap = flowNodes.stream().collect(
                Collectors.toMap(FundInvoiceFlowVO::getFlowNodeId, Function.identity()));

        // 签报流程名称 = 发票单位名称 + 流程类型名称
        String flowName = flowApi.getNameByNodeId(flowNodes.get(0).getFlowNodeId());
        FundInvoice invoice = fundInvoiceMapper.single(invoiceId);
        wrapper.setName(invoice.getUnitName() + flowName);

        // 判断流程是否已结束
        // 根据最后一个节点的状态可判断整个流程的状态
        if (flowNodes.get(flowNodes.size() - 1).getOperation().equals(FlowOperationType.UN_EXECUTED.value())) {

            // 1.先获取流程流转的当前节点<currentNode>
            // 2.再判断<loginUser>是否为<currentNode>的审批人
            //   2-1.如果不是，则所有节点信息全部为只读
            //   2-2.如果是，则判断是否为该流程的分配者
            //      a.如果不是分配者，则只能编辑当前节点的按钮操作<operation>和意见<suggestion>
            //      b.如果是分配者，则可以编辑以下：
            //        当前节点的按钮操作<operation>和意见<suggestion>
            //        其他节点的审批人<approver>
            //      c.特殊节点

            // 1.先获取流程流转的当前节点
            List<NodeOperationReqVO> operations = new ArrayList<>();
            List<String> flowNodeIds = new ArrayList<>();
            flowNodes.forEach(flowNode -> {
                operations.add(NodeOperationReqVO.builder()
                        .flowNodeId(flowNode.getFlowNodeId())
                        .operation(flowNode.getOperation())
                        .build());
                flowNodeIds.add(flowNode.getFlowNodeId());
            });
            // 当前节点id
            String currentNodeId = getCurrentNode(operations);
            if (!StringUtils.isEmpty(currentNodeId)) {
                FundInvoiceFlowVO currentNode = flowNodeMap.get(currentNodeId);
                // 2.判断登录用户是否为当前节点的审批人
                // 2-1.如果不是，则所有节点信息全部为只读
                if (!userId.equals(currentNode.getApprover())) {
                    flowNodes.forEach(flowNode -> nodes.add(buildFlowNode(flowNode, currentNodeId, 0)));
                }
                // 2-2.如果是，则判断是否为该流程的分配者
                else {
                    // 用户的所有流程角色
                    List<Long> flowRoleIds = flowRoleUserApi.getRoleIdByUserId(userId);
                    // 2-2-c.特殊节点-收账员（最后一个节点为收账员，需填写部分必填业务字段）
                    if (isLastNode(currentNodeId, flowNodeIds)) {
                        flowNodes.forEach(flowNode -> nodes.add(buildFlowNode(flowNode, currentNodeId, 3)));
                    }
                    // 2-2-a.当前用户是分配者
                    else if (flowRoleIds.contains(currentNode.getAssigner())) {
                        flowNodes.forEach(flowNode -> nodes.add(buildFlowNode(flowNode, currentNodeId, 2)));
                    }
                    // 2-2-b.当前用户不是分配者
                    else {
                        flowNodes.forEach(flowNode -> nodes.add(buildFlowNode(flowNode, currentNodeId, 1)));
                    }

                }
            }
            // 当前节点序号
            wrapper.setIndex(getCurrentNodeIndex(operations));
        }
        // 如果流程已结束，则所有节点只读，不能操作
        else {
            flowNodes.forEach(flowNode -> nodes.add(buildFlowNode(flowNode, "", 0)));
            // 当前节点序号
            wrapper.setIndex(0);
        }

        // 解决用户被解除流程角色后，审批人下拉框显示id而非姓名的情况
        for (NodeInfoVO vo : nodes) {
            // 如果当前用户是审批人，而该用户已被解除该流程角色时，加上该用户姓名
            if (userId.equals(vo.getApprover().getValue())) {
                List<Long> userIds = vo.getApproverSelect()
                        .stream().map(SelectVO::getId).collect(Collectors.toList());
                if (!userIds.contains(Long.valueOf(userId))) {
                    vo.getApproverSelect().add(SelectVO.builder()
                            .id(Long.valueOf(userId))
                            .label(userInfo.getNickName())
                            .build());
                }
            }
        }
        wrapper.setNodes(nodes);
        return wrapper;
    }

    /**
     * 获取流程的节点列表
     */
    private List<FundInvoiceFlowVO> getFlowNodes(Long invoiceId) {
        List<FundInvoiceFlow> flows = fundInvoiceFlowMapper.createLambdaQuery()
                .andEq(FundInvoiceFlow::getInvoiceId, invoiceId)
                .select();
        return Optional.ofNullable(flows)
                .orElse(new ArrayList<>())
                .stream()
                .map(flow -> {
                    FundInvoiceFlowVO vo = BeanConverter.convert(flow, FundInvoiceFlowVO.class);
                    // 此处节点个数有限，循环中使用rpc接口无妨
                    UserInfo userInfo = userApi.getUserInfoById(flow.getUserId());
                    vo.setBusinessId(flow.getInvoiceId());
                    vo.setRoleDesc(flowRoleApi.getNameById(flow.getFlowRoleId()));
                    vo.setNickName(userInfo == null ? null : userInfo.getNickName());
                    vo.setApprover(flow.getUserId());
                    return vo;
                }).collect(Collectors.toList());
    }

    /**
     * 获取流程流转的当前节点id
     *
     * @param list 已排序
     * @return 当前节点的id
     */
    private String getCurrentNode(List<NodeOperationReqVO> list) {
        // 通过检查operation状态来确定流程流传到哪个节点
        for (NodeOperationReqVO vo : list) {
            if (FlowOperationType.UN_EXECUTED.value().equals(vo.getOperation())) {
                return vo.getFlowNodeId();
            }
        }
        return null;
    }

    /**
     * 获取流程流转的当前节点序号
     */
    private Integer getCurrentNodeIndex(List<NodeOperationReqVO> list) {
        for (int i = 0; i < list.size(); i++) {
            if (FlowOperationType.UN_EXECUTED.value().equals(list.get(i).getOperation())) {
                return i;
            }
        }
        return null;
    }

    /**
     * 组装流程节点
     */
    private NodeInfoVO buildFlowNode(FundInvoiceFlowVO flowNode, String currentNodeId, Integer type) {
        NodeInfoVO result = new NodeInfoVO();
        BeanConverter.copyProp(flowNode, result);
        // 判断是否为当前节点
        boolean isCurrentNode = currentNodeId.equals(flowNode.getFlowNodeId());
        // 审批人是否只读
        boolean approverReadOnly;
        // 操作按钮是否隐藏
        boolean operationHidden;
        // 意见是否只读
        boolean suggestionReadOnly;
        // 是否请求下拉框的数据
        boolean requestSelectData = true;
        // 可编辑字段
        List<String> filedList = new ArrayList<>();
        switch (type) {
            // 审批节点（非分配者）
            case 1:
                approverReadOnly = true;
                operationHidden = !isCurrentNode;
                suggestionReadOnly = !isCurrentNode;
                break;
            // 审批节点（分配者）
            case 2:
                approverReadOnly = isCurrentNode;
                operationHidden = !isCurrentNode;
                suggestionReadOnly = !isCurrentNode;
                break;
            // 审批节点（收账员）
            case 3:
                approverReadOnly = isCurrentNode;
                operationHidden = !isCurrentNode;
                suggestionReadOnly = !isCurrentNode;
                filedList.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(
                        NodeAccessFiled.INVOICE_COLLECTOR.value())));
                break;
            // 默认只读
            default:
                approverReadOnly = true;
                operationHidden = true;
                suggestionReadOnly = true;
                requestSelectData = false;
        }
        result.setApprover(NodeApproverVO.builder()
                .value(flowNode.getApprover())
                .readOnly(approverReadOnly)
                .build());
        result.setOperation(NodeOperationVO.builder()
                .value(flowNode.getOperation())
                .hidden(operationHidden)
                .build());
        result.setSuggestion(NodeSuggestionVO.builder()
                .value(flowNode.getSuggestion())
                .readOnly(suggestionReadOnly)
                .build());
        // 如果节点已经操作过，则不返回下拉框列表；如果节点未操作，则返回
        if (requestSelectData && flowNode.getOperation().equals(FlowOperationType.UN_EXECUTED.value())) {
            result.setApproverSelect(getApproverSelect(flowNode.getFlowNodeId(), flowNode.getBusinessId()));
        }
        result.setRoleDesc(flowNode.getRoleDesc());
        result.setApproveTime(DateUtil.dateToString(flowNode.getUpdateTime()));
        result.setFiledList(filedList);
        return result;
    }

    /**
     * 查询审批人下拉框列表
     */
    private List<SelectVO> getApproverSelect(String flowNodeId, Long businessId) {
        FundInvoiceFlow flow = fundInvoiceFlowMapper.createLambdaQuery()
                .andEq(FundInvoiceFlow::getInvoiceId, businessId)
                .andEq(FundInvoiceFlow::getFlowNodeId, flowNodeId)
                .single();
        // 查询该节点的流程角色所对应的用户
        List<Integer> userIds = flowRoleUserApi.getUserIdByRoleId(flow.getFlowRoleId());
        Map<Integer, String> userMap = userApi.getUserMapById(userIds);
        return userMap.entrySet().stream().map(item ->
                SelectVO.builder()
                        .id(Long.valueOf(item.getKey()))
                        .label(item.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
