package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatVO implements Serializable {

    private static final long serialVersionUID = -511249135896611529L;

    private Long id;

    @Schema(description = "期间")
    private String periodTime;

    @Schema(description = "部门名称")
    private String unitName;

    @Schema(description = "集团信息")
    private String groupName;

    @Schema(description = "录入人")
    private String createMan;

    @Schema(description = "期初余额")
    private String beginAmount;

    @Schema(description = "本期增加")
    private String addAmount;

    @Schema(description = "核销收款")
    private String verifyAmount;

    @Schema(description = "本期减少")
    private String reduceAmount;

    @Schema(description = "本期收款冻结")
    private String collectionFrozen;

    @Schema(description = "本期使用冻结")
    private String useFrozen;

    @Schema(description = "本期退款冻结")
    private String refundFrozen;

    @Schema(description = "本期退款")
    private String refund;

    @Schema(description = "本期余额")
    private String balance;

    @Schema(description = "积累开票金额")
    private String totalInvoiceAmount;

    @Schema(description = "积累入账金额")
    private String totalEnterAmount;
}
