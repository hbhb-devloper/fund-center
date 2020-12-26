package com.hbhb.cw.fundcenter.web.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangxiaogang
 * @since 2020-08-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundUnitImportVO implements Serializable {
    private static final long serialVersionUID = -7853016157475239110L;

    @ExcelProperty(value = "单位名称", index = 0)
    private String unitName;

    @ExcelProperty(value = "单位编号", index = 1)
    private String unitCode;

    @ExcelProperty(value = "状态(0-禁用,1-启用)", index = 2)
    private Boolean state;

    @ExcelProperty(value = "初始资金", index = 3)
    private BigDecimal initialAmount;
}
