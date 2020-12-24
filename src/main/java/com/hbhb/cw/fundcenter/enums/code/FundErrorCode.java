package com.hbhb.cw.fundcenter.enums.code;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum FundErrorCode {

    FUND_QUERY_LACK_OF_UNIT_ID("FU001", "fund.query.lack.of.unit_id"),
    FUND_INVOICE_USER_INCORRECT("FU002", "fund.invoice.user.incorrect"),
    FUND_INVOICE_HAS_NO_WRITE_ACCESS("FU003", "fund.invoice.has.no.write.access"),
    FUND_INVOICE_PERMISSION_DENIED("FU004", "fund.invoice.permission.denied"),
    FUND_INVOICE_NOT_INPUT_ERROR("FU005", "fund.invoice.not.input.error"),

    ;

    private final String code;

    private final String message;

    FundErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
