package com.hbhb.cw.fundcenter.web.controller;

import com.hbhb.core.utils.ExcelUtil;
import com.hbhb.cw.flowcenter.vo.FlowApproveVO;
import com.hbhb.cw.flowcenter.vo.FlowToApproveVO;
import com.hbhb.cw.flowcenter.vo.FlowWrapperVO;
import com.hbhb.cw.fundcenter.enums.code.FundErrorCode;
import com.hbhb.cw.fundcenter.exception.FundException;
import com.hbhb.cw.fundcenter.service.FundInvoiceFlowService;
import com.hbhb.cw.fundcenter.service.FundInvoiceService;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceExtraVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceExportVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceReqVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceResVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceVO;
import com.hbhb.web.annotation.UserId;

import org.beetl.sql.core.page.DefaultPageResult;
import org.beetl.sql.core.page.PageResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author wxg
 * @since 2020-09-03
 */
@Tag(name = "客户资金-发票")
@RestController
@RequestMapping("/invoice")
public class FundInvoiceController {

    @Resource
    private FundInvoiceService fundInvoiceService;
    @Resource
    private FundInvoiceFlowService fundInvoiceFlowService;

    @Operation(summary = "发票信息列表 | 新版本 fileLabel,invoiceContentLabel,businessLabel,stateLabel,isCancellationLabel去掉 isFile -> hasFile")
    @GetMapping("/list")
    public PageResult<InvoiceResVO> pageFundInvoice(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            @Parameter(hidden = true) @UserId Integer userId, InvoiceReqVO cond) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        if (cond.getUnitId() == null) {
            return new DefaultPageResult<>();
        }
        return fundInvoiceService.pageFundInvoice(pageNum, pageSize, userId, cond);
    }

    @Operation(summary = "获取发票详情 | 新版本 /info/{id} -> /{id}, 出参结构")
    @GetMapping("/{id}")
    public InvoiceVO getFundInvoice(@Parameter(description = "发票id") @PathVariable Long id) {
        return fundInvoiceService.getFundInvoiceInfo(id);
    }

    @Operation(summary = "新增发票信息 | 新版本 /add -> /")
    @PostMapping("")
    public void addFundInvoice(@Parameter(description = "发票信息实体") @RequestBody InvoiceVO vo,
                               @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceService.addFundInvoice(vo, userId);
    }

    @Operation(summary = "修改发票信息 | 新版本 /update -> /")
    @PutMapping("")
    public void updateFundInvoice(@Parameter(description = "发票信息实体") @RequestBody InvoiceVO vo,
                                  @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceService.updateFundInvoice(vo, userId);
    }

    @Operation(summary = "修改发票信息 | 新版本 /update-info -> /additional", description = "收账员补充发票信息")
    @PutMapping("/additional")
    public void updateFundInvoiceExtra(@RequestBody FundInvoiceExtraVO vo,
                                       @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceService.updateFundInvoiceExtra(vo, userId);
    }

    @Operation(summary = "发票作废 | 新版本 /cancellation/{id} -> /discard/{id}")
    @PutMapping("/discard/{id}")
    public void updateCancellation(@Parameter(description = "发票id") @PathVariable Long id,
                                   @Parameter(description = "是否作废") @RequestParam Byte cancellation,
                                   @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceService.discardFundInvoice(id, cancellation, userId);
    }

    @Operation(summary = "删除发票 | 新版本 /delete/{id} -> /{id}")
    @DeleteMapping("/{id}")
    public void deleteFundInvoice(@Parameter(description = "id") @PathVariable Long id,
                                  @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceService.deleteFundInvoice(id, userId);
    }

    @Operation(summary = "删除发票附件 | 新版本 /delete/file/{fileId} -> /file/{fileId}")
    @DeleteMapping("/file/{fileId}")
    public void deleteFundInvoiceFile(@Parameter(description = "附件id") @PathVariable Long fileId,
                                      @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceService.deleteFundInvoiceFile(fileId, userId);
    }

    @Operation(summary = "导出发票信息列表")
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @Parameter(hidden = true) @UserId Integer userId,
                       @RequestBody InvoiceReqVO cond) {
        if (cond.getUnitId() == null) {
            throw new FundException(FundErrorCode.FUND_QUERY_LACK_OF_UNIT_ID);
        }
        List<InvoiceExportVO> list = fundInvoiceService.getExportList(cond, userId);
        String fileName = ExcelUtil.encodingFileName(request, "发票信息列表");
        ExcelUtil.export2Web(response, fileName, fileName, InvoiceExportVO.class, list);
    }

    @Operation(summary = "发起审批 | 新版本 大改")
    @PostMapping("/to-approve")
    public void toApprover(@RequestBody FlowToApproveVO vo,
                           @Parameter(hidden = true) @UserId Integer userId) {
        vo.setUserId(userId);
        fundInvoiceService.toApprove(vo);
    }

    @Operation(summary = "审批 | 新版本 大改")
    @PostMapping("/approve")
    public void approve(@RequestBody FlowApproveVO vo,
                        @Parameter(hidden = true) @UserId Integer userId) {
        fundInvoiceFlowService.approve(vo, userId);
    }

    @Operation(summary = "获取发票流程 | 新版本 /fund/invoice/flow/list/{invoiceId} -> /fund/invoice/{id}/flows, 出参 InvoiceId -> invoiceId")
    @GetMapping("/{id}/flow")
    public FlowWrapperVO getInvoiceFlows(@Parameter(description = "id") @PathVariable Long id,
                                         @Parameter(hidden = true) @UserId Integer userId) {
        return fundInvoiceFlowService.getFlowInfo(id, userId);
    }
}
