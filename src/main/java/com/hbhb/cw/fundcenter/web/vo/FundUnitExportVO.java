package com.hbhb.cw.fundcenter.web.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundUnitExportVO {


    @ColumnWidth(40)
    @ExcelProperty(value = "单位名称", index = 0)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String unitName;

    @ColumnWidth(20)
    @ExcelProperty(value = "单位编号", index = 1)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String unitCode;

    @ColumnWidth(10)
    @ExcelProperty(value = "状态(0-禁用,1-启用)", index = 2)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private Byte state;

    @ColumnWidth(40)
    @ExcelProperty(value = "初始资金", index = 3)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal initialAmount;
}
