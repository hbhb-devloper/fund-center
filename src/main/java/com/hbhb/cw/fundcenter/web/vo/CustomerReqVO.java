package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: hsp
 * @Date: 2020/8/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReqVO implements Serializable {

    private static final long serialVersionUID = 8636566762257432603L;

    @Schema(description = "单位id")
    private Integer unitId;

    @Schema(description = "款项类型")
    private String amountType;

    @Schema(description = "集团信息")
    private String groupName;

    @Schema(description = "业务类型")
    private String busType;

    @Schema(description = "资金流向")
    private String fundFlows;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "落地时间")
    private String peopleDownTime;

    @Schema(description = "录入人")
    private String userName;

    @Schema(description = "流程状态")
    private String state;

    @Schema(description = "年份")
    private String fundYear;
}
