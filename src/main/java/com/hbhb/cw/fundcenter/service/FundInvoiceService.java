package com.hbhb.cw.fundcenter.service;

import com.hbhb.cw.flowcenter.vo.FlowToApproveVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceExtraVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceExportVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceReqVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceResVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author wxg
 * @since 2020-09-03
 */
public interface FundInvoiceService {

    /**
     * 分页获取发票列表
     */
    PageResult<InvoiceResVO> pageFundInvoice(Integer pageNum, Integer pageSize,
                                             Integer userId, InvoiceReqVO cond);

    /**
     * 查询发票详情
     */
    InvoiceVO getFundInvoiceInfo(Long id);

    /**
     * 新增发票信息
     */
    void addFundInvoice(InvoiceVO vo, Integer userId);

    /**
     * 修改发票信息
     */
    void updateFundInvoice(InvoiceVO vo, Integer userId);

    /**
     * 收账员修改发票信息
     */
    void updateFundInvoiceExtra(FundInvoiceExtraVO vo, Integer userId);

    /**
     * 发票作废
     */
    void discardFundInvoice(Long id, Boolean cancellation, Integer userId);

    /**
     * 删除发票信息
     */
    void deleteFundInvoice(Long id, Integer userId);

    /**
     * 删除发票附件
     */
    void deleteFundInvoiceFile(Long fileId, Integer userId);

    /**
     * 导出发票
     */
    List<InvoiceExportVO> getExportList(InvoiceReqVO cond, Integer userId);

    /**
     * 发起发票审批
     */
    void toApprove(FlowToApproveVO vo);
}
