//package com.hbhb.cw.fundcenter.web.controller;
//
//import com.hbhb.cw.fundcenter.service.FundInvoiceNoticeService;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//
///**
// * @author wxg
// * @since 2020-09-22
// */
//@Tag(name = "客户资金-工作台")
//@RequestMapping("/relocation/notice")
//@RestController
//public class FundInvoiceNoticeController {
//
//    @Resource
//    private FundInvoiceNoticeService fundInvoiceNoticeService;
//
//    @Operation(summary = "客户资金提醒列表")
//    @GetMapping("/list")
//    public Page<FundInvoiceNoticeResVO> getInvoiceNotice(@ApiParam("筛选条件") FundInvoiceNoticeVO noticeVO,
//                                                         @ApiParam(value = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
//                                                         @ApiParam(value = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
//                                                         @CurrentUser @ApiIgnore LoginUser loginUser) {
//        noticeVO.setUserId(loginUser.getUser().getId());
//        pageNum = pageNum == null ? 1 : pageNum;
//        pageSize = pageSize == null ? 10 : pageSize;
//        return noticeService.getInvoiceNoticeList(noticeVO,pageNum, pageSize);
//    }
//
//    @ApiOperation("设置提醒内容为已读")
//    @PutMapping("update/{id}")
//    public void updateNoticeState(@PathVariable Long id) {
//        noticeService.updateInvoiceNotice(id);
//    }
//}
