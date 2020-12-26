package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundInvoiceNoticeReqVO implements Serializable {
    private static final long serialVersionUID = 1806204760813430073L;

    private Integer userId;
    private String unitNum;
    private BigDecimal amountMin;
    private BigDecimal amountMax;
}
