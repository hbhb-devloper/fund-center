package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVO implements Serializable {
    private static final long serialVersionUID = 5519062501586407747L;

    private Long id;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "款项类型")
    private Integer amountType;

    @Schema(description = "业务类型")
    private Integer busType;

    @Schema(description = "集团信息")
    private String groupName;

    @Schema(description = "资金流向")
    private Integer fundFlows;

    @Schema(description = "资金到账时间")
    private Date intoAccountDate;

    @Schema(description = "是否开具发票")
    private String hasInvoice;

    @Schema(description = "已开票金额")
    private BigDecimal preInvoiceAmount;

    @Schema(description = "发票编号")
    private String invoiceCode;

    @Schema(description = "发票金额")
    private BigDecimal invoiceAmount;

    @Schema(description = "备注")
    private String customerNote;

    @Schema(description = "附件")
    private List<CustomerFileVO> files;
}
