//package com.hbhb.cw.fundcenter.web.controller;
//
//import com.alibaba.excel.EasyExcel;
//import com.hbhb.cw.common.exception.BizException;
//import com.hbhb.cw.common.exception.BizStatus;
//import com.hbhb.cw.model.FundUnit;
//import com.hbhb.cw.service.FundUnitService;
//import com.hbhb.cw.service.listener.FundListener;
//import com.hbhb.cw.utils.ExcelUtil;
//import com.hbhb.cw.web.vo.FundUnitExportVO;
//import com.hbhb.cw.web.vo.FundUnitImportVO;
//import com.hbhb.cw.web.vo.FundUnitReqVO;
//import com.hbhb.springboot.web.view.Page;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Copyright (c) 2020 Choice, Inc. All Rights Reserved. Choice Proprietary and Confidential.
// *
// * @author jiyu@myweimai.com
// * @since 2020-08-13
// */
//@Api(tags = "客户资金-单位信息")
//@RequestMapping("/fund")
//@RestController
//@Slf4j
//public class FundUnitController {
//
//    @Resource
//    private FundUnitService fundUnitService;
//
//    @ApiOperation("客户资金单位信息导入")
//    @PostMapping("/import")
//    public List<String> importFundUnit(MultipartFile file) {
//        long begin = System.currentTimeMillis();
//        String fileName = file.getOriginalFilename();
//        fundUnitService.judgeFileName(fileName);
//        try {
//            EasyExcel.read(file.getInputStream(), FundUnitImportVO.class,
//                    new FundListener(fundUnitService)).sheet().doRead();
//            List<String> msg = fundUnitService.getMsg();
//            if (msg.size() != 0) {
//                return msg;
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new BizException(BizStatus.INVOICE_DATA_IMPORT_ERROR.getCode());
//        }
//
//        log.info("客户资金单位导入入结束，总共耗时：" + (System.currentTimeMillis() - begin) / 1000 + "s");
//        return null;
//    }
//
//    @ApiOperation("单位信息列表")
//    @GetMapping("/list")
//    public Page<FundUnit> fundUnitList(
//            @ApiParam(value = "单位编号") @RequestParam(required = false) String unitCode,
//            @ApiParam(value = "单位名称") @RequestParam(required = false) String unitName,
//            @ApiParam(value = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
//            @ApiParam(value = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize) {
//        pageNum = pageNum == null ? 1 : pageNum;
//        pageSize = pageSize == null ? 10 : pageSize;
//        return fundUnitService.getFundUnitList(unitCode, unitName, pageNum, pageSize);
//    }
//
//    @ApiOperation("修改单位信息")
//    @PutMapping("/update")
//    public void updateFundUnit(@RequestBody FundUnitReqVO fundUnitReqVO) {
//        fundUnitService.updateFundUnit(fundUnitReqVO);
//    }
//
//    @ApiOperation("根据条件查询单位信息列表")
//    @GetMapping("/cond-list")
//    public List<FundUnit> fundUnit(FundUnitReqVO vo) {
//        return fundUnitService.getFundUnit(vo);
//    }
//
//    @ApiOperation("客户资金单位信息模板下载")
//    @PostMapping("/export")
//    public void export(HttpServletRequest request, HttpServletResponse response) {
//        List<FundUnitExportVO> exportVO = new ArrayList<>();
//        String fileName = ExcelUtil.encodingFileName(request, "发票单位信息导入模板");
//        ExcelUtil.export2Web(response, fileName, fileName, FundUnitExportVO.class, exportVO);
//    }
//}
