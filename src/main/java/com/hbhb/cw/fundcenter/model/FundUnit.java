package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundUnit implements Serializable {
    private static final long serialVersionUID = -7600229529124623L;

    @AutoID
    private Long id;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "单位编号")
    private String unitCode;

    @Schema(description = "是否启用")
    private Boolean state;

    @Schema(description = "期初金额")
    private BigDecimal initialAmount;
}