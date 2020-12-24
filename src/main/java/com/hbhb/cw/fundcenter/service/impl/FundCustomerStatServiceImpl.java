package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.fundcenter.mapper.FundCustomerMapper;
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
public class FundCustomerStatServiceImpl implements FundCustomerStatService {

    @Resource
    private FundCustomerMapper fundCustomerMapper;
    @Resource
    private UnitApiExp unitApi;

    @Override
    @SuppressWarnings(value = {"rawtypes"})
    public PageResult<CustomerStatVO> pageCustomerStat(Integer pageNum, Integer pageSize,
                                                       CustomerStatReqVO cond) {
        List<Integer> unitIds = unitApi.getSubUnit(cond.getUnitId());
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        return fundCustomerMapper.selectStatPageByCond(cond, unitIds, request);
    }

//    @Override
//    public CustomerStatVO getStatDetail(Long id) {
//        //获取该条客户资金数据
//        FundCustomerHistory customerHistory = historyMapper.selectByPrimaryKey(id);
//        //获取集团名
//        String groupName = customerHistory.getGroupName();
//        //落地时间
//        Date implementTime = customerHistory.getImplementTime();
//        String implementTimeStr = DateUtil.dateToString(implementTime);
//        //预开票金额
//        BigDecimal preInvoiceAmount = customerHistory.getPreInvoiceAmount();
//        preInvoiceAmount = creatNewBigDecimal(preInvoiceAmount);
//        //获得该集团信息 -- 部门 -- 期初余额 -- 集团名
//        FundCustomerStatVO customerStatVO = customerStatMapper.getStartAmount(groupName);
//        //期初余额
//        BigDecimal beginAmount = customerStatVO.getBeginAmount();
//        beginAmount = creatNewBigDecimal(beginAmount);
//        return setCustomerStatVO(groupName, implementTimeStr, customerStatVO, beginAmount,
//                preInvoiceAmount);
//    }

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

//    /**
//     * 查询到的bigDecimal金额是否为空 实例化
//     */
//    private BigDecimal creatNewBigDecimal(BigDecimal bigDecimal) {
//        if (bigDecimal == null) {
//            bigDecimal = new BigDecimal("0.00");
//        }
//        return bigDecimal;
//    }
//
//    /**
//     * 计算资金额度统计数据
//     */
//    private FundCustomerStatVO setCustomerStatVO(
//            String groupName, String implementTimeStr,
//            FundCustomerStatVO customerStatVO,
//            BigDecimal beginAmount, BigDecimal preInvoiceAmount) {
//        //本期增加
//        BigDecimal thisAddAmount = customerStatMapper
//                .getAmount(groupName, FundFlowsState.FUND_FLOWS_RECEIVABLE.value(),
//                        FundFlowsState.STATE_APPROVAL_BY.value(), implementTimeStr);
//        //thisAddAmount = creatNewBigDecimal(thisAddAmount);
//        //本期减少
//        BigDecimal thisReduceAmount = customerStatMapper
//                .getAmount(groupName, FundFlowsState.FUND_FLOWS_USE.value(),
//                        FundFlowsState.STATE_APPROVAL_BY.value(),
//                        implementTimeStr);
//        //thisReduceAmount = creatNewBigDecimal(thisReduceAmount);
//        //本期收款冻结
//        BigDecimal thisCollectionFrozen = customerStatMapper
//                .getAmount(groupName, FundFlowsState.FUND_FLOWS_RECEIVABLE.value(),
//                        FundFlowsState.STATE_APPROVAL_BEING.value(), implementTimeStr);
//        //thisCollectionFrozen = creatNewBigDecimal(thisCollectionFrozen);
//        //本期使用冻结
//        BigDecimal thisUseFrozen = customerStatMapper
//                .getAmount(groupName, FundFlowsState.FUND_FLOWS_USE.value(),
//                        FundFlowsState.STATE_APPROVAL_BEING.value(),
//                        implementTimeStr);
//        //thisUseFrozen = creatNewBigDecimal(thisUseFrozen);
//        //本期退款冻结
//        BigDecimal thisRefundFrozen = customerStatMapper
//                .getAmount(groupName, FundFlowsState.FUND_FLOWS_REFUND.value(),
//                        FundFlowsState.STATE_APPROVAL_BEING.value(),
//                        implementTimeStr);
//        //thisRefundFrozen = creatNewBigDecimal(thisRefundFrozen);
//        //本期退款
//        BigDecimal thisRefund = customerStatMapper
//                .getAmount(groupName, FundFlowsState.FUND_FLOWS_REFUND.value(),
//                        FundFlowsState.STATE_APPROVAL_BY.value(),
//                        implementTimeStr);
//        //thisRefund = creatNewBigDecimal(thisRefund);
//        //本期余额 -- 根据公式计算=期初余额 +本期增加-冻结-本期减少-本期退款
//        BigDecimal thisBalance = beginAmount.add(thisAddAmount).subtract(thisCollectionFrozen)
//                .subtract(thisUseFrozen).subtract(thisRefundFrozen)
//                .subtract(thisReduceAmount).subtract(thisRefund);
//        //累积入账金额=期初余额+本期增加-该集团截止目前所有审批通过退款
//        BigDecimal totalEnterAmount = beginAmount.add(thisAddAmount).subtract(thisRefund);
//        //截止目前所有已经审批通过的收款表单中的已开票金额累计
//        BigDecimal passRecpreInvAmount = customerStatMapper
//                .getPreInvoiceAmountOfApprovedReceipts(groupName,
//                        FundFlowsState.FUND_FLOWS_RECEIVABLE.value(),
//                        FundFlowsState.STATE_APPROVAL_BY.value(),
//                        implementTimeStr);
//        passRecpreInvAmount = creatNewBigDecimal(passRecpreInvAmount);
//        //累积开票金额=期初已开票金额+截止目前所有已经审批通过的收款表单中的已开票金额累计
//        BigDecimal totalInvoiceAmount = preInvoiceAmount.add(passRecpreInvAmount);
//        customerStatVO.setAddAmount(thisAddAmount);
//        customerStatVO.setVerifyAmount(new BigDecimal("0.00"));
//        customerStatVO.setReduceAmount(thisReduceAmount);
//        customerStatVO.setCollectionFrozen(thisCollectionFrozen);
//        customerStatVO.setUseFrozen(thisUseFrozen);
//        customerStatVO.setRefundFrozen(thisRefundFrozen);
//        customerStatVO.setRefund(thisRefund);
//        customerStatVO.setBalance(thisBalance);
//        customerStatVO.setTotalInvoiceAmount(totalInvoiceAmount);
//        customerStatVO.setTotalEnterAmount(totalEnterAmount);
//        return customerStatVO;
//    }
}
