package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.flowcenter.api.FlowRoleUserApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.flow-center}", url = "${feign-url}", contextId = "FlowRoleUserApi", path = "/role/user")
public interface FlowRoleUserApiExp extends FlowRoleUserApi {
}
