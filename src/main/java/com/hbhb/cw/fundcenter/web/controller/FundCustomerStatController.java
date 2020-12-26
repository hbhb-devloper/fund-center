package com.hbhb.cw.fundcenter.web.controller;

import com.hbhb.core.utils.ExcelUtil;
import com.hbhb.cw.fundcenter.service.FundCustomerStatService;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatExportVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatVO;

import org.beetl.sql.core.page.DefaultPageResult;
import org.beetl.sql.core.page.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @author tpson
 */
@Tag(name = "客户资金-统计")
@RestController
@RequestMapping("/stat")
public class FundCustomerStatController {

    @Resource
    private FundCustomerStatService fundCustomerStatService;

    @Operation(summary = "获取客户资金统计（分页）")
    @GetMapping("/list")
    public PageResult<CustomerStatVO> pageFundCustomerStat(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            CustomerStatReqVO cond) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        if (cond.getUnitId() == null) {
            return new DefaultPageResult<>();
        }
        return fundCustomerStatService.pageCustomerStat(pageNum, pageSize, cond);
    }

    @Operation(summary = "获取客户资金统计详细数据")
    @GetMapping("/detail/{id}")
    public CustomerStatVO getFundCustomerStatDetail(@PathVariable Long id) {
        return fundCustomerStatService.getStatDetail(id);
    }

    @Operation(summary = "导出客户资金统计数据")
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestBody CustomerStatReqVO cond) {
        List<CustomerStatExportVO> exportList = fundCustomerStatService.getExportList(cond);
        String fileName = ExcelUtil.encodingFileName(request, "客户资金台账数据统计表");
        ExcelUtil.export2Web(response, fileName, "客户资金台账统计数据", CustomerStatVO.class, exportList);
    }
}
