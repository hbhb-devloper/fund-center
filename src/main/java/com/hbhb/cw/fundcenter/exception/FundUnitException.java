package com.hbhb.cw.fundcenter.exception;

import com.hbhb.core.bean.MessageConvert;
import com.hbhb.cw.fundcenter.enums.code.FundUnitErrorCode;
import com.hbhb.web.exception.BusinessException;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public class FundUnitException extends BusinessException {
    private static final long serialVersionUID = 4396818948911942520L;

    private final String code;

    public FundUnitException(FundUnitErrorCode errorCode) {
        super(errorCode.getCode(), MessageConvert.convert(errorCode.getMessage()));
        this.code = errorCode.getCode();
    }
}
