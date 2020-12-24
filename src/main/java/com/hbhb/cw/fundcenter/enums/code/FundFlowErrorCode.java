package com.hbhb.cw.fundcenter.enums.code;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum FundFlowErrorCode {

    NOT_EXIST_FLOW("FU101", "not.exist.flow"),
    EXCEED_LIMIT_FLOW("FU102", "exceed.limit.flow"),
    LACK_OF_FLOW("FU103", "lack.of.flow"),
    LACK_OF_NODE_PROP("FU104", "lack.of.flow.node.prop"),
    LACK_OF_FLOW_ROLE("FU105", "lack.of.flow.role"),
    LOCK_OF_APPROVAL_ROLE("FU106", "lack.of.approval.role"),
    NOT_ALL_APPROVERS_ASSIGNED("FU107", "not.all.approvers.assigned"),

    ;

    private final String code;

    private final String message;

    FundFlowErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
