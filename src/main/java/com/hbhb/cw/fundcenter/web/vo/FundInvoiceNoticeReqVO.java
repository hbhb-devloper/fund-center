package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundInvoiceNoticeReqVO implements Serializable {
    private static final long serialVersionUID = 1806204760813430073L;

    private Long id;

    @Schema(description = "发票id")
    private Long invoiceId;

    @Schema(description = "接收人id")
    private Integer receiver;

    @Schema(description = "发起人id")
    private Integer promoter;

    @Schema(description = "提醒内容")
    private String content;

    @Schema(description = "状态")
    private Integer state;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "流程类型id")
    private Long flowTypeId;
}
