package com.hbhb.cw.fundcenter.exception;

import com.hbhb.core.bean.MessageConvert;
import com.hbhb.cw.fundcenter.enums.code.FundErrorCode;
import com.hbhb.web.exception.BusinessException;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public class FundException extends BusinessException {
    private static final long serialVersionUID = -4003093842616359982L;

    private final String code;

    public FundException(FundErrorCode errorCode) {
        super(errorCode.getCode(), MessageConvert.convert(errorCode.getMessage()));
        this.code = errorCode.getCode();
    }
}
