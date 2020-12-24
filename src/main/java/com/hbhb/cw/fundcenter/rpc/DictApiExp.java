package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.systemcenter.api.DictApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.system-center}", url = "${feign-url}", contextId = "DictApi", path = "dict")
public interface DictApiExp extends DictApi {
}
