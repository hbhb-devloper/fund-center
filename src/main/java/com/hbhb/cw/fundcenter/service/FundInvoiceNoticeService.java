package com.hbhb.cw.fundcenter.service;

import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeReqVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeResVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author wxg
 * @since 2020-09-09
 */
public interface FundInvoiceNoticeService {

    /**
     * 分页获取客户资金发票提醒列表
     */
    PageResult<FundInvoiceNoticeResVO> pageInvoiceNotice(Integer pageNum, Integer pageSize,
                                                         FundInvoiceNoticeReqVO cond);

    /**
     * 获取用户的待办提醒列表
     */
    List<FundInvoiceNoticeVO> listInvoiceNotice(Integer userId);

    /**
     * 修改提醒消息状态
     */
    void changeNoticeState(Long id);

    /**
     * 统计用户的提醒消息数量
     */
    Long countNotice(Integer userId);
}
