package com.hbhb.cw.fundcenter.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wxg
 * @since 2020-09-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceVO implements Serializable {
    private static final long serialVersionUID = -7116942850028928464L;

    private Long id;

    @Schema(description = "客户经理")
    private String clientManager;

    @Schema(description = "开票金额")
    private BigDecimal invoiceAmount;

    @Schema(description = "发票账户")
    private String invoiceAccount;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "单位编号")
    private String unitNumber;

    @Schema(description = "发票内容")
    private String invoiceContent;

    @Schema(description = "办理业务")
    private String business;

    @Schema(description = "欠费月份:yyyy-MM")
    private String arrearageMonth;

    @Schema(description = "欠费金额:(元)")
    private String arrearageMoney;

    @Schema(description = "计费号")
    private String billingNumber;

    @Schema(description = "推送地址")
    private String pushAddress;

    @Schema(description = "发票版本号")
    private String versions;

    @Schema(description = "发票编号")
    private String invoiceNumber;

    @Schema(description = "到账时间")
    private String accountTime;

    @Schema(description = "到账金额")
    private String accountMoney;

    @Schema(description = "开票人")
    private String invoiceUser;

    @Schema(description = "流程状态值")
    private Integer state;

    @Schema(description = "状态名称")
    private String stateLabel;

    @Schema(description = "是否作废")
    private Boolean isCancellation;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "附件")
    private List<InvoiceFileVO> files;
}
