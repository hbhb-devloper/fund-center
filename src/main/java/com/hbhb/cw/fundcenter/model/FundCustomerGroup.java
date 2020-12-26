package com.hbhb.cw.fundcenter.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaokang
 * @since 2020-12-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundCustomerGroup implements Serializable {
    private static final long serialVersionUID = 1232054893226248470L;

    private Long id;
    /**
     * 单位id
     */
    private Integer unitId;
    /**
     * 集团信息（编号+名称）
     */
    private String groupName;
    /**
     * 期初余额
     */
    private BigDecimal accountStart;
    /**
     * 已开票金额
     */
    private BigDecimal invoiceStart;
    /**
     * 备注
     */
    private String remark;
    private Integer isUse;
}
