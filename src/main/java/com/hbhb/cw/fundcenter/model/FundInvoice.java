package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundInvoice implements Serializable {
    private static final long serialVersionUID = -8546297474468347140L;

    @AutoID
    private Long id;

    private Integer unitId;

    private String clientManager;

    private BigDecimal invoiceAmount;

    private String unitNumber;

    private String unitName;

    private Integer invoiceContent;

    private Integer business;

    private String versions;

    private String invoiceNumber;

    private Date arrearageMonth;

    private BigDecimal arrearageMoney;

    private String invoiceUser;

    private Date invoiceCreateTime;

    private Integer state;

    private Date accountTime;

    private BigDecimal accountMoney;

    private String invoiceAccount;

    private Boolean isCancellation;

    private String billingNumber;

    private String pushAddress;

    private Integer deleteFlag;

    private String remark;

    private Date createTime;

}