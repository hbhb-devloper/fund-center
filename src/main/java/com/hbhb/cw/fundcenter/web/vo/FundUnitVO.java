package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundUnitVO implements Serializable {
    private static final long serialVersionUID = 7925614430191161580L;

    private Long id;

    @Schema(description = "单位编号")
    private String unitCode;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "状态(0-未启用,1-启用)")
    private Boolean state;

}
