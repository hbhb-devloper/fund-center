package com.hbhb.cw.fundcenter.web.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFileVO implements Serializable {
    private static final long serialVersionUID = 7706749686645288071L;

    @Schema(description = "标题")
    private String fileName;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "时间")
    private Date uploadTime;

    @Schema(description = "大小")
    private String fileSize;
}