package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.flowcenter.api.FlowNodeApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.flow-center}", url = "${feign-url}", contextId = "FlowNodeApi", path = "/node")
public interface FlowNodeApiExp extends FlowNodeApi {
}
