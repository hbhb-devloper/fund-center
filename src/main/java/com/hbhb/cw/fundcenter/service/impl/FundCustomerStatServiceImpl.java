package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.core.utils.DateUtil;
import com.hbhb.core.utils.NumberUtil;
import com.hbhb.cw.flowcenter.enums.FlowState;
import com.hbhb.cw.fundcenter.enums.FundFlowsState;
import com.hbhb.cw.fundcenter.mapper.FundCustomerGroupMapper;
import com.hbhb.cw.fundcenter.mapper.FundCustomerMapper;
import com.hbhb.cw.fundcenter.model.FundCustomer;
import com.hbhb.cw.fundcenter.model.FundCustomerGroup;
import com.hbhb.cw.fundcenter.rpc.UnitApiExp;
import com.hbhb.cw.fundcenter.service.FundCustomerStatService;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatExportVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatVO;

import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tpson
 */
@Service
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class FundCustomerStatServiceImpl implements FundCustomerStatService {

    @Resource
    private FundCustomerMapper fundCustomerMapper;
    @Resource
    private FundCustomerGroupMapper fundCustomerGroupMapper;
    @Resource
    private UnitApiExp unitApi;

    @Override
    public PageResult<CustomerStatVO> pageCustomerStat(Integer pageNum, Integer pageSize,
                                                       CustomerStatReqVO cond) {
        List<Integer> unitIds = unitApi.getSubUnit(cond.getUnitId());
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        return fundCustomerMapper.selectStatPageByCond(cond, unitIds, request);
    }

    @Override
    public CustomerStatVO getStatDetail(Long id) {
        CustomerStatVO vo = new CustomerStatVO();
        FundCustomer fundCustomer = fundCustomerMapper.single(id);
        // 集团名称
        String groupName = fundCustomer.getGroupName();
        // 落地时间
        String implementTime = DateUtil.dateToString(fundCustomer.getImplementTime());
        // 预开票金额
        BigDecimal preInvoiceAmount = fundCustomer.getPreInvoiceAmount() == null
                ? new BigDecimal("0.00") : fundCustomer.getPreInvoiceAmount();

        // 查询集团信息
        FundCustomerGroup group = fundCustomerGroupMapper.createLambdaQuery()
                .andEq(FundCustomerGroup::getGroupName, groupName)
                .single();
        if (group == null) {
            return vo;
        }
        // 期初余额
        BigDecimal beginAmount = StringUtils.isEmpty(group.getAccountStart())
                ? new BigDecimal("0.00") : group.getAccountStart();
        vo.setUnitName(unitApi.getUnitInfo(group.getUnitId()).getUnitName());
        vo.setGroupName(group.getGroupName());
        vo.setBeginAmount(NumberUtil.format(beginAmount.doubleValue()));

        // 本期增加
        BigDecimal thisAddAmount = this.getAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_RECEIVABLE.value(), FlowState.APPROVED.value());
        // 本期减少
        BigDecimal thisReduceAmount = this.getAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_USE.value(), FlowState.APPROVED.value());
        // 本期收款冻结
        BigDecimal thisCollectionFrozen = this.getAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_RECEIVABLE.value(), FlowState.APPROVING.value());
        // 本期使用冻结
        BigDecimal thisUseFrozen = this.getAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_USE.value(), FlowState.APPROVING.value());
        // 本期退款冻结
        BigDecimal thisRefundFrozen = this.getAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_REFUND.value(), FlowState.APPROVING.value());
        // 本期退款
        BigDecimal thisRefund = this.getAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_REFUND.value(), FlowState.APPROVED.value());
        // 本期余额=期初余额+本期增加-冻结-本期减少-本期退款
        BigDecimal thisBalance = beginAmount.add(thisAddAmount)
                .subtract(thisCollectionFrozen)
                .subtract(thisUseFrozen).subtract(thisRefundFrozen)
                .subtract(thisReduceAmount).subtract(thisRefund);
        // 累积入账金额=期初余额+本期增加-该集团截止目前所有审批通过退款
        BigDecimal totalEnterAmount = beginAmount.add(thisAddAmount).subtract(thisRefund);
        // 截止目前所有已经审批通过的收款表单中的已开票金额累计
        BigDecimal approvedAmount = this.getApprovedAmount(groupName, implementTime,
                FundFlowsState.FUND_FLOWS_RECEIVABLE.value(), FlowState.APPROVED.value());
        //累积开票金额=期初已开票金额+截止目前所有已经审批通过的收款表单中的已开票金额累计
        BigDecimal totalInvoiceAmount = preInvoiceAmount.add(approvedAmount);
        vo.setAddAmount(NumberUtil.format(thisAddAmount.doubleValue()));
        vo.setVerifyAmount("0.00");
        vo.setReduceAmount(NumberUtil.format(thisReduceAmount.doubleValue()));
        vo.setCollectionFrozen(NumberUtil.format(thisCollectionFrozen.doubleValue()));
        vo.setUseFrozen(NumberUtil.format(thisUseFrozen.doubleValue()));
        vo.setRefundFrozen(NumberUtil.format(thisRefundFrozen.doubleValue()));
        vo.setRefund(NumberUtil.format(thisRefund.doubleValue()));
        vo.setBalance(NumberUtil.format(thisBalance.doubleValue()));
        vo.setTotalInvoiceAmount(NumberUtil.format(totalInvoiceAmount.doubleValue()));
        vo.setTotalEnterAmount(NumberUtil.format(totalEnterAmount.doubleValue()));
        return vo;
    }

    private BigDecimal getAmount(String groupName, String implementTime,
                                 Integer fundFlows, Integer flowState) {
        List<FundCustomer> list = fundCustomerMapper.createLambdaQuery()
                .andEq(FundCustomer::getGroupName, groupName)
                .andLessEq(FundCustomer::getImplementTime, implementTime)
                .andEq(FundCustomer::getFundFlows, fundFlows)
                .andEq(FundCustomer::getState, flowState)
                .andIsNotNull(FundCustomer::getAmount)
                .select(FundCustomer::getAmount);
        return list.stream()
                .map(FundCustomer::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getApprovedAmount(String groupName, String implementTime,
                                         Integer fundFlows, Integer flowState) {
        List<FundCustomer> list = fundCustomerMapper.createLambdaQuery()
                .andEq(FundCustomer::getGroupName, groupName)
                .andLessEq(FundCustomer::getImplementTime, implementTime)
                .andEq(FundCustomer::getFundFlows, fundFlows)
                .andEq(FundCustomer::getState, flowState)
                .andIsNotNull(FundCustomer::getPreInvoiceAmount)
                .select(FundCustomer::getPreInvoiceAmount);
        return list.stream()
                .map(FundCustomer::getPreInvoiceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<CustomerStatExportVO> getExportList(CustomerStatReqVO cond) {
        PageResult<CustomerStatVO> pageResult = this.pageCustomerStat(1, Integer.MAX_VALUE, cond);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return new ArrayList<>();
        }
        return pageResult.getList().stream()
                .map(vo -> BeanConverter.convert(vo, CustomerStatExportVO.class))
                .collect(Collectors.toList());
    }
}
