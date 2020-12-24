package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundCustomer implements Serializable {
    private static final long serialVersionUID = 9169458074259465970L;

    @AutoID
    private Long id;
    /**
     * 单位id
     */
    private Integer unitId;
    /**
     * 编号
     */
    private String code;
    /**
     * 集团信息
     */
    private String groupName;
    /**
     * 办理业务
     */
    private Integer busType;
    /**
     * 资金方向
     */
    private Integer fundFlows;
    /**
     * 款项类型
     */
    private Integer amountType;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 到账时间
     */
    private Date intoAccountDate;
    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;
    /**
     * 发票编号
     */
    private String invoiceCode;
    /**
     * 是否开具发票1开2未开
     */
    private Integer hasInvoice;
    /**
     * 预开金额
     */
    private BigDecimal preInvoiceAmount;
    /**
     * 落地人
     */
    private String implementer;
    /**
     * 落地时间
     */
    private Date implementTime;
    /**
     * 流程状态
     */
    private Integer state;
    /**
     * 备注
     */
    private String remark;
    /**
     * 录入人
     */
    private Integer createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 集团id
     */
    private Long groupId;
}