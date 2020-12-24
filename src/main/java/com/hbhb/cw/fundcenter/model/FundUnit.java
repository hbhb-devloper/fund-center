package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundUnit implements Serializable {
    private static final long serialVersionUID = -7600229529124623L;

    @AutoID
    private Long id;

    private String unitName;

    private String unitCode;

    private Boolean state;

    private BigDecimal initialAmount;
}