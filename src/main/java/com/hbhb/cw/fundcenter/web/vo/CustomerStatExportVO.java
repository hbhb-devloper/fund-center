package com.hbhb.cw.fundcenter.web.vo;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatExportVO implements Serializable {

    private static final long serialVersionUID = -511249135896611529L;

    private Long id;

    @ColumnWidth(30)
    @ExcelProperty(value = "期间", index = 0)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String periodTime;


    @ExcelProperty(value = "部门名称", index = 1)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String unitName;


    @ExcelProperty(value = "集团信息", index = 2)
    @ColumnWidth(65)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String groupName;

    @ExcelProperty(value = "录入人", index = 3)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String createMan;


    @ExcelProperty(value = "期初余额", index = 4)
    @ColumnWidth(20)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal beginAmount;


    @ExcelProperty(value = "本期增加", index = 5)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal addAmount;


    @ExcelProperty(value = "核销收款", index = 6)
    @ColumnWidth(20)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal verifyAmount;


    @ExcelProperty(value = "本期减少", index = 7)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal reduceAmount;


    @ExcelProperty(value = "本期收款冻结", index = 8)
    @ColumnWidth(15)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal collectionFrozen;


    @ExcelProperty(value = "本期使用冻结", index = 9)
    @ColumnWidth(15)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal useFrozen;


    @ExcelProperty(value = "本期退款冻结", index = 10)
    @ColumnWidth(15)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal refundFrozen;


    @ExcelProperty(value = "本期退款", index = 11)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal refund;


    @ExcelProperty(value = "本期余额", index = 12)
    @ColumnWidth(10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal balance;


    @ExcelProperty(value = "积累开票金额", index = 13)
    @ColumnWidth(15)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal totalInvoiceAmount;


    @ExcelProperty(value = "积累入账金额", index = 14)
    @ColumnWidth(15)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private BigDecimal totalEnterAmount;
}
