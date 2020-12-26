package com.hbhb.cw.fundcenter.web.controller;

import com.hbhb.core.utils.ExcelUtil;
import com.hbhb.cw.flowcenter.vo.NodeInfoVO;
import com.hbhb.cw.fundcenter.service.FundCustomerService;
import com.hbhb.cw.fundcenter.web.vo.CustomerExportVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerResVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerVO;

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
 * @author Wuyuxin
 * @since 2020-09-3
 */
@Tag(name = "客户资金-历史数据")
@RestController
@RequestMapping("/customer")
public class FundCustomerController {

    @Resource
    private FundCustomerService fundCustomerService;

    @Operation(summary = "获取客户资金列表（分页）")
    @GetMapping("/list")
    public PageResult<CustomerResVO> pageFundCustomer(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            CustomerReqVO cond) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        if (cond.getUnitId() == null) {
            return new DefaultPageResult<>();
        }
        return fundCustomerService.pageFundCustomer(pageNum, pageSize, cond);
    }

    @Operation(summary = "获取客户资金详情")
    @GetMapping("/{id}")
    public CustomerVO getFundCustomerInfo(@Parameter(description = "页码，默认为1") @PathVariable Integer id) {
        return fundCustomerService.getFundCustomerInfo(id);
    }

    @Operation(summary = "导出客户资金数据")
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestBody CustomerReqVO cond) {
        List<CustomerExportVO> exportList = fundCustomerService.getExportList(cond);
        String fileName = ExcelUtil.encodingFileName(request, "客户资金台账数据表");
        ExcelUtil.export2Web(response, fileName, "客户资金台账数据", CustomerExportVO.class, exportList);
    }

    @Operation(summary = "获取客户资金的流程信息")
    @GetMapping("/{id}/flow")
    public List<NodeInfoVO> getFundCustomerFlow(@PathVariable Long id) {
        return fundCustomerService.getFundCustomerFlow(id);
    }
}

