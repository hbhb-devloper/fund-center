package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundCustomerFlow implements Serializable {
    private static final long serialVersionUID = 6159332833479524159L;
    
    @AutoID
    private Long id;

    private Long customerId;

    private String flowNodeId;

    private Long flowRoleId;

    private String userId;

    private String roleDesc;

    private Long assigner;

    private Byte controlAccess;

    private Byte isJoin;

    private Integer operation;

    private String suggestion;

    private Byte isDelete;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}