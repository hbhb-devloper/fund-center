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
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceExportVO implements Serializable {
    private static final long serialVersionUID = -3259818191774730433L;

    @ExcelIgnore
    private Long id;

    @ColumnWidth(40)
    @ExcelProperty(value = "发票开具单位", index = 0)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "发票开具单位")
    private String invoiceUnit;

    @ColumnWidth(40)
    @ExcelProperty(value = "客户经理", index = 1)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "客户经理", required = true)
    private String clientManager;

    @ColumnWidth(40)
    @ExcelProperty(value = "开票金额", index = 2)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "开票金额", required = true)
    private String invoiceAmount;


    @ColumnWidth(40)
    @ExcelProperty(value = "单位名称", index = 3)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "单位名称", required = true)
    private String unitName;

    @ColumnWidth(40)
    @ExcelProperty(value = "单位编号", index = 4)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "单位编号", required = true)
    private String unitNumber;

    @ExcelIgnore
    private Integer invoiceContent;

    @ColumnWidth(40)
    @ExcelProperty(value = "发票内容", index = 5)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "发票内容", required = true)
    private String invoiceContentLabel;

    @ExcelIgnore
    @Schema(description = "办理业值", required = true)
    private Integer business;

    @ColumnWidth(40)
    @ExcelProperty(value = "办理业务内容", index = 6)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "办理业务内容")
    private String businessLabel;

    @ColumnWidth(40)
    @ExcelProperty(value = "欠费时间", index = 7)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "欠费时间", required = true)
    private String arrearageMonth;

    @ColumnWidth(40)
    @ExcelProperty(value = "欠费金额:(元)", index = 8)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "欠费金额:(元)", required = true)
    private String arrearageMoney;

    @ColumnWidth(40)
    @ExcelProperty(value = "计费号", index = 9)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "计费号", required = true)
    private String billingNumber;


    @ColumnWidth(40)
    @ExcelProperty(value = "发票账户", index = 10)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "发票账户", required = true)
    private String invoiceAccount;

    @ColumnWidth(40)
    @ExcelProperty(value = "发票版本号", index = 11)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "发票版本号", required = true)
    private String versions;

    @ColumnWidth(40)
    @ExcelProperty(value = "发票编号", index = 12)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "发票编号", required = true)
    private String invoiceNumber;

    @ColumnWidth(40)
    @ExcelProperty(value = "到账时间", index = 13)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "到账时间", required = true)
    private String accountTime;

    @ColumnWidth(40)
    @ExcelProperty(value = "到账金额", index = 14)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "到账金额", required = true)
    private String accountMoney;

    @ColumnWidth(40)
    @ExcelProperty(value = "开票人", index = 15)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "开票人", required = true)
    private String invoiceUser;

    @ColumnWidth(40)
    @ExcelProperty(value = "出票时间", index = 16)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "开票时间", required = true)
    private String invoiceCreateTime;

    @ExcelIgnore
    @Schema(description = "流程状值", required = true)
    private Integer state;

    @ColumnWidth(40)
    @ExcelProperty(value = "状态名称", index = 17)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "状态名称")
    private String stateLabel;

    @ExcelIgnore
    private Boolean isCancellation;

    @ColumnWidth(40)
    @ExcelProperty(value = "是否作废", index = 18)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "是否作废", required = true)
    private String isCancellationLabel;

    @ExcelIgnore
    private String isFile;

    @ColumnWidth(40)
    @ExcelProperty(value = "是否含附件:0-不包含、1-包含", index = 19)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    private String fileLabel;


}
