package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.flowcenter.api.FlowNodeNoticeApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.flow-center}", url = "${feign-url}", contextId = "FlowNodeNoticeApi", path = "/node/notice")
public interface FlowNodeNoticeApiExp extends FlowNodeNoticeApi {
}
