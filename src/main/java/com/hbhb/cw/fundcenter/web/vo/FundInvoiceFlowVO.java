package com.hbhb.cw.fundcenter.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundInvoiceFlowVO {
    private Long id;
    private Long businessId;
    private String flowNodeId;
    private Long flowRoleId;
    private String approverRole;
    private String nickName;
    private Integer approver;
    private Integer operation;
    private String suggestion;
    private Boolean controlAccess;
    private Boolean isJoin;
    private Long assigner;
    private String roleDesc;
    private String updateTime;
}
