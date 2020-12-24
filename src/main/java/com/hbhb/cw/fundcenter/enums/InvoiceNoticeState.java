package com.hbhb.cw.fundcenter.enums;

public enum InvoiceNoticeState {

    /**
     * 未读
     */
    UN_READ(0),
    /**
     * 已读
     */
    READ(1),

    ;

    private final Integer value;

    InvoiceNoticeState(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
