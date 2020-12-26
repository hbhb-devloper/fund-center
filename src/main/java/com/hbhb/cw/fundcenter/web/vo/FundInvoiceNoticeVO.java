package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-10-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundInvoiceNoticeVO implements Serializable {
    private static final long serialVersionUID = 5887236295418970992L;

    private Long id;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "项目id")
    private Long projectId;
    @Schema(description = "日期")
    private String date;
    @Schema(description = "签报人")
    private String userName;
}
