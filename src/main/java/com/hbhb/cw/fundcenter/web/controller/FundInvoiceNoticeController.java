package com.hbhb.cw.fundcenter.web.controller;

import com.hbhb.cw.fundcenter.api.NoticeApi;
import com.hbhb.cw.fundcenter.service.FundInvoiceNoticeService;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeReqVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeResVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeVO;
import com.hbhb.web.annotation.UserId;

import org.beetl.sql.core.page.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author wxg
 * @since 2020-09-22
 */
@Tag(name = "客户资金-工作台 | 新版本 /relocation/notice -> /notice")
@RequestMapping("/notice")
@RestController
public class FundInvoiceNoticeController implements NoticeApi {

    @Resource
    private FundInvoiceNoticeService fundInvoiceNoticeService;

    @Operation(summary = "客户资金提醒列表 | 新版本 入参结构 firstNum -> amountMin, twoNum -> amountMax")
    @GetMapping("/list")
    public PageResult<FundInvoiceNoticeResVO> pageInvoiceNotice(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            @Parameter(description = "单位编号") @RequestParam(required = false) String unitNum,
            @Parameter(description = "金额最小值") @RequestParam(required = false) BigDecimal amountMin,
            @Parameter(description = "金额最大值") @RequestParam(required = false) BigDecimal amountMax,
            @Parameter(hidden = true) @UserId Integer userId) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return fundInvoiceNoticeService.pageInvoiceNotice(pageNum, pageSize,
                FundInvoiceNoticeReqVO.builder()
                        .userId(userId)
                        .unitNum(unitNum)
                        .amountMin(amountMin)
                        .amountMax(amountMax)
                        .build());
    }

    @Operation(summary = "获取登录用户的待办提醒")
    @GetMapping("/home")
    public List<FundInvoiceNoticeVO> getUserInvoiceNotice(@Parameter(hidden = true) @UserId Integer userId) {
        return fundInvoiceNoticeService.listInvoiceNotice(userId);
    }

    @Operation(summary = "更新提醒消息为已读 | 新版本 update/{id} -> /{id}")
    @PutMapping("/{id}")
    public void changeNoticeState(@Parameter(description = "id") @PathVariable Long id) {
        fundInvoiceNoticeService.changeNoticeState(id);
    }

    @Operation(summary = "统计用户的提醒消息数量")
    @Override
    public Long countNotice(@Parameter(description = "用户id") Integer userId) {
        return fundInvoiceNoticeService.countNotice(userId);
    }
}
