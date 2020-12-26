package com.hbhb.cw.fundcenter.enums.code;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum FundUnitErrorCode {

    FUND_UNIT_IMPORT_ERROR("FU201", "fund.unit.import.error"),
    FUND_UNIT_FILE_NAME_ERROR("FU202", "fund.unit.file.name.error"),
    FUND_UNIT_FILE_EXTENSION_ERROR("FU203", "fund.unit.file.extension.error"),

    ;

    private final String code;

    private final String message;

    FundUnitErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
