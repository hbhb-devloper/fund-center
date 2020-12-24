package com.hbhb.cw.fundcenter.service;

import com.hbhb.cw.flowcenter.vo.FlowApproveVO;
import com.hbhb.cw.flowcenter.vo.FlowWrapperVO;

/**
 * @author wxg
 * @since 2020-09-08
 */
public interface FundInvoiceFlowService {

    /**
     * 流程审批
     */
    void approve(FlowApproveVO vo, Integer userId);

    /**
     * 获取流程信息
     */
    FlowWrapperVO getFlowInfo(Long invoiceId, Integer userId);
}
