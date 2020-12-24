package com.hbhb.cw.fundcenter.web.vo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceReqVO {

    @Schema(description = "单位id")
    private Integer unitId;

    @Schema(description = "父类单位id")
    private Integer parentId;

    @Schema(description = "开票金额")
    private BigDecimal invoiceAmount;

    @Schema(description = "业务内容")
    private Integer business;

    @Schema(description = "流程状态")
    private Integer state;

    @Schema(description = "客户经理")
    private String clientManager;

    @Schema(description = "是否作废")
    private Boolean isCancellation;

    @Schema(description = "单位编号")
    private String unitNumber;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "发票编号")
    private String invoiceNumber;

}
