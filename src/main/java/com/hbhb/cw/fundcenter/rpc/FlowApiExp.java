package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.flowcenter.api.FlowApi;
import com.hbhb.cw.flowcenter.api.FlowNodePropApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.flow-center}", url = "${feign-url}", contextId = "FlowApi", path = "/")
public interface FlowApiExp extends FlowApi {
}
