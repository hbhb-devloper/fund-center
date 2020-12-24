package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.systemcenter.api.FileApi;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yzc
 * @since 2020-11-27
 */
@FeignClient(value = "${provider.system-center}", url = "${feign-url}", contextId = "FileApi", path = "file")
public interface FileApiExp extends FileApi {
}
