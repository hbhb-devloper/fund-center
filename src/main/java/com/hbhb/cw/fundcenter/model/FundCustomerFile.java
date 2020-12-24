package com.hbhb.cw.fundcenter.model;

import org.beetl.sql.annotation.entity.AutoID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundCustomerFile implements Serializable {
    private static final long serialVersionUID = -8096911344694113753L;

    @AutoID
    private Long id;
    /**
     * 客户资金id
     */
    private Long customerId;
    /**
     * 文件id
     */
    private Integer fileId;
    /**
     * 项目类型
     */
    private Integer itemType;
    /**
     * 附件名称
     */
    private String title;
    /**
     * 上传时间
     */
    private Date createTime;
    /**
     * 大小
     */
    private String fileSize;
    /**
     * 上传人
     */
    private String createBy;
}