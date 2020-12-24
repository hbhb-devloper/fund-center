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
public class FundInvoiceFile implements Serializable {
    private static final long serialVersionUID = 2012207187479365279L;

    @AutoID
    private Long id;

    private Long invoiceId;

    private Boolean required;

    private Date createTime;

    private String createBy;

    private Integer fileId;
}