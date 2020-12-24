package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxg
 * @since 2020-09-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceFileVO implements Serializable {
    private static final long serialVersionUID = -7859783814571507868L;

    private Long id;

    @Schema(description = "标题")
    private String fileName;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "时间")
    private Date uploadTime;

    @Schema(description = "大小")
    private String fileSize;

    @Schema(description = "文件地址")
    private String filePath;

    @Schema(description = "文件id")
    private Integer fileId;
}
