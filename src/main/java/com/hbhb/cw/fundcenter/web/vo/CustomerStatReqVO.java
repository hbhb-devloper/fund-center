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
public class CustomerStatReqVO implements Serializable {
    private static final long serialVersionUID = 1827680395183172414L;

    @Schema(description = "单位id")
    private Integer unitId;

    @Schema(description = "款项类型")
    private Integer amountType;

    @Schema(description = "集团信息")
    private String groupName;

    @Schema(description = "业务类型")
    private Integer busType;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "录入人")
    private String userName;

    @Schema(description = "零余额显示")
    private Integer isBalanceZero;
}
