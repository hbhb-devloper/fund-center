package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundInvoiceFlow implements Serializable {
    private static final long serialVersionUID = -7514663918845138636L;

    @AutoID
    private Long id;

    private Long invoiceId;

    private String flowNodeId;

    private Long flowRoleId;

    private Integer userId;

    private String roleDesc;

    private Long assigner;

    private Boolean controlAccess;

    private Boolean isJoin;

    private Integer operation;

    private String suggestion;

    private Byte isDelete;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}