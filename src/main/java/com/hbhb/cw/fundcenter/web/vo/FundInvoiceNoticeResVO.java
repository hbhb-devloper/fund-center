package com.hbhb.cw.fundcenter.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundInvoiceNoticeResVO implements Serializable {
    private static final long serialVersionUID = -4281340494920740929L;

    private Long id;

    @Schema(description = "发票id")
    private Integer invoiceId;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "提醒内容")
    private String content;

    @Schema(description = "发起时金额")
    private String amount;

    @JsonIgnore
    @Schema(description = "流程类型id")
    private Long flowTypeId;

    @Schema(description = "流程类型名称")
    private String flowType;

    @Schema(description = "发起人")
    private String userName;

    @JsonIgnore
    @Schema(description = "发起单位id")
    private Integer unitId;

    @Schema(description = "发起单位名称")
    private String unitName;

    @Schema(description = "发起时间")
    private String createTime;

    @Schema(description = "状态值")
    private Integer state;

    @Schema(description = "状态名称")
    private String stateLabel;
}
