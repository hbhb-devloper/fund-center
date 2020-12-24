package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResVO implements Serializable {
    private static final long serialVersionUID = 8636566762257432603L;

    private Long id;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "录入人")
    private String createMan;

    @Schema(description = "时间")
    private String createTime;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "集团信息")
    private String groupName;

    @Schema(description = "业务类型")
    private String busType;

    @Schema(description = "资金流向")
    private String fundFlows;

    @Schema(description = "款项类型")
    private String amountType;

    @Schema(description = "金额")
    private String amount;

    @Schema(description = "发票金额")
    private String invoiceAmount;

    @Schema(description = "发票编号")
    private String invoiceCode;

    @Schema(description = "发票预开金额")
    private String preInvoiceAmount;

    @Schema(description = "落地人")
    private String peopleDown;

    @Schema(description = "落地时间")
    private String peopleDownTime;

    @Schema(description = "流程状态")
    private String state;

    @Schema(description = "文件信息(是否有附件)")
    private String document;

}
