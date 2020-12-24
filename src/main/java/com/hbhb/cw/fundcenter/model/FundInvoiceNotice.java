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
public class FundInvoiceNotice implements Serializable {
    private static final long serialVersionUID = 7575609947165410675L;

    @AutoID
    private Long id;

    private Long invoiceId;

    private Integer receiver;

    private Integer promoter;

    private String content;

    private Integer state;

    private Integer priority;

    private Date createTime;

    private Long flowTypeId;
}