//package com.hbhb.cw.fundcenter.service;
//
//import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeReqVO;
//import com.hbhb.cw.systemcenter.vo.UserInfo;
//import com.hbhb.cw.web.vo.FundInvoiceNoticeReqVO;
//import com.hbhb.cw.web.vo.FundInvoiceNoticeResVO;
//import com.hbhb.cw.web.vo.FundInvoiceNoticeVO;
//import com.hbhb.cw.web.vo.WorkBenchAgendaVO;
//import com.hbhb.springboot.web.view.Page;
//
//import java.util.List;
//
///**
// * @author wxg
// * @since 2020-09-09
// */
//public interface FundInvoiceNoticeService {
//
//    /**
//     * 跟据条件查询分页显示提醒内容
//     *
//     * @return 提醒信息列表
//     */
//    Page<FundInvoiceNoticeResVO> getInvoiceNoticeList(FundInvoiceNoticeVO noticeVO, Integer pageNum, Integer pageSize);
//
//    /**
//     * 修改提醒装态
//     */
//    void updateInvoiceNotice(Long id);
//
//    /**
//     * 通过发票Id把该发票的所有提醒改成已读
//     */
//    void updateNoticeState(Long invoiceId);
//
//    /**
//     * 跟据用户id统计提醒数量
//     */
//    int getNoticeAccount(Integer id);
//
//    /**
//     * 跟据登录用户获取待办提醒
//     */
//    List<WorkBenchAgendaVO> getInvoiceNotice(UserInfo user);
//}
