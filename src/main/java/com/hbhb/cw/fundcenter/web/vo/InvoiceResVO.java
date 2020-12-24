package com.hbhb.cw.fundcenter.web.vo;

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
public class InvoiceResVO implements Serializable {
    private static final long serialVersionUID = 792531342440666079L;

    private Long id;

    @Schema(description = "发票开具单位")
    private String invoiceUnit;

    @Schema(description = "客户经理")
    private String clientManager;

    @Schema(description = "发票金额")
    private String invoiceAmount;

    @Schema(description = "发票账户")
    private String invoiceAccount;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "单位编号")
    private String unitNumber;

    @Schema(description = "发票内容")
    private String invoiceContent;

    @Schema(description = "办理业务值")
    private String business;

    @Schema(description = "欠费月份:yyyy-MM")
    private String arrearageMonth;

    @Schema(description = "欠费金额:(元)")
    private String arrearageMoney;

    @Schema(description = "计费号")
    private String billingNumber;

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

    @Schema(description = "开票时间:yyyy-MM-dd")
    private String invoiceCreateTime;

    @Schema(description = "流程状态")
    private String state;

    @Schema(description = "是否作废")
    private String isCancellation;

    @Schema(description = "是否含附件(0-不含,1-含)")
    private Boolean hasFile;
}
