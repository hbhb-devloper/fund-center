package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundInvoiceLastReqVO implements Serializable {
    private static final long serialVersionUID = 4507231430339092136L;

    private Long id;

    @Schema(description = "发票版本号", required = true)
    private String versions;

    @Schema(description = "发票编号", required = true)
    private String invoiceNumber;

    @Schema(description = "到账时间", required = true)
    private String accountTime;

    @Schema(description = "到账金额", required = true)
    private String accountMoney;

    @Schema(description = "开票人", required = true)
    private String invoiceUser;

    @Schema(description = "开票时间:yyyy-MM-dd", required = true)
    private String invoiceCreateTime;
}
