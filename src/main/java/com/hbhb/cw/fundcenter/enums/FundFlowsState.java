package com.hbhb.cw.fundcenter.enums;

public enum FundFlowsState {

    /**
     * 客户资金-资金流向-使用
     */
    FUND_FLOWS_USE(1),

    /**
     * 客户资金-资金流向-收款
     */
    FUND_FLOWS_RECEIVABLE(2),

    /**
     * 客户资金-资金流向-退款
     */
    FUND_FLOWS_REFUND(3),
    ;

    private final Integer value;

    public Integer value() {
        return this.value;
    }

    public Integer getValue() {
        return value;
    }

    FundFlowsState(Integer value) {
        this.value = value;
    }
}
