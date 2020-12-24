package com.hbhb.cw.fundcenter.enums;

public enum NodeAccessFiled {

    /**
     * 发票收账员可编辑的字段
     */
    INVOICE_COLLECTOR("versions,invoiceNumber"),

    ;

    private final String value;

    NodeAccessFiled(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }


}
