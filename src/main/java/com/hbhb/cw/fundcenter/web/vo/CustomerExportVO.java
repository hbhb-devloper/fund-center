package com.hbhb.cw.fundcenter.web.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wuyuxin
 * @since 2020-09-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExportVO implements Serializable {
    private static final long serialVersionUID = -4649989786562442719L;

    @ExcelIgnore
    private Long id;

    @ColumnWidth(30)
    @ExcelProperty(value = "编号", index = 0)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String code;


    @ExcelProperty(value = "录入时间", index = 1)
    @ColumnWidth(30)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private Date createTime;


    @ExcelProperty(value = "单位名称", index = 2)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String unitName;


    @ExcelProperty(value = "集团信息", index = 3)
    @ColumnWidth(60)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String groupName;


    @ExcelProperty(value = "业务类型", index = 4)
    @ColumnWidth(20)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String busType;


    @ExcelProperty(value = "资金流向", index = 5)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String fundFlows;


    @ExcelProperty(value = "款项类型", index = 6)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String amountType;


    @ExcelProperty(value = "金额", index = 7)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal amount;


    @ExcelProperty(value = "发票金额", index = 8)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal invoiceAmount;


    @ExcelProperty(value = "发票编号", index = 9)
    @ColumnWidth(25)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String invoiceCode;


    @ExcelProperty(value = "已开票金额", index = 10)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal preInvoiceAmount;


    @ExcelProperty(value = "录入人", index = 11)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String createMan;


    @ExcelProperty(value = "落地人", index = 12)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String peopleDown;


    @ExcelProperty(value = "落地时间", index = 13)
    @ColumnWidth(25)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private Date peopleDownTime;


    @ExcelProperty(value = "流程状态", index = 14)
    @ColumnWidth(20)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String state;
}
