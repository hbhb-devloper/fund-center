package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.flowcenter.api.FlowNodePropApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.flow-center}", url = "${feign-url}", contextId = "FlowNodePropApi", path = "/node/prop")
public interface FlowNodePropApiExp extends FlowNodePropApi {
}
