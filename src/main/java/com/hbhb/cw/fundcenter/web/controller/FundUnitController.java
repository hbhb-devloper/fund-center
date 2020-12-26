package com.hbhb.cw.fundcenter.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hbhb.cw.fundcenter.enums.code.FundUnitErrorCode;
import com.hbhb.cw.fundcenter.exception.FundUnitException;
import com.hbhb.cw.fundcenter.model.FundUnit;
import com.hbhb.cw.fundcenter.service.FundUnitService;
import com.hbhb.cw.fundcenter.service.listener.FundUnitListener;
import com.hbhb.cw.fundcenter.web.vo.FundUnitImportVO;
import com.hbhb.cw.fundcenter.web.vo.FundUnitVO;

import org.beetl.sql.core.page.PageResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiaogang
 * @since 2020-08-13
 */
@Tag(name = "客户资金-单位")
@RequestMapping("/unit")
@RestController
@Slf4j
public class FundUnitController {

    @Resource
    private FundUnitService fundUnitService;

    @Operation(summary = "分页获取单位信息列表")
    @GetMapping("/list")
    public PageResult<FundUnit> pageFundUnit(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            @Parameter(description = "单位编号") @RequestParam(required = false) String unitCode,
            @Parameter(description = "单位名称") @RequestParam(required = false) String unitName) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return fundUnitService.pageFundUnit(pageNum, pageSize, unitCode, unitName);
    }

    @Operation(summary = "根据条件查询单位信息下拉框列表")
    @GetMapping("/select")
    public List<FundUnitVO> getFundUnitSelect(
            @Parameter(description = "单位编号") @RequestParam(required = false) String unitCode,
            @Parameter(description = "单位名称") @RequestParam(required = false) String unitName) {
        return fundUnitService.listFundUnit(unitCode, unitName);
    }

    @Operation(summary = "修改单位信息")
    @PutMapping("")
    public void updateFundUnit(@RequestBody FundUnitVO vo) {
        fundUnitService.updateFundUnit(vo);
    }

    @Operation(summary = "客户资金单位信息导入")
    @PostMapping("/import")
    public List<String> importFundUnit(MultipartFile file) {
        long begin = System.currentTimeMillis();
        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            throw new FundUnitException(FundUnitErrorCode.FUND_UNIT_FILE_NAME_ERROR);
        }
        // 校验文件后缀名
        int suffix = fileName.lastIndexOf(".");
        String name = fileName.substring(suffix);
        if (!ExcelTypeEnum.XLS.getValue().equals(name) && !ExcelTypeEnum.XLSX.getValue().equals(name)) {
            throw new FundUnitException(FundUnitErrorCode.FUND_UNIT_FILE_EXTENSION_ERROR);
        }
        try {
            EasyExcel.read(file.getInputStream(), FundUnitImportVO.class,
                    new FundUnitListener(fundUnitService)).sheet().doRead();
            List<String> msg = fundUnitService.getMsg();
            if (msg.size() != 0) {
                return msg;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new FundUnitException(FundUnitErrorCode.FUND_UNIT_IMPORT_ERROR);
        }
        log.info("客户资金单位信息导入结束，总共耗时：" + (System.currentTimeMillis() - begin) / 1000 + "s");
        return null;
    }
}
