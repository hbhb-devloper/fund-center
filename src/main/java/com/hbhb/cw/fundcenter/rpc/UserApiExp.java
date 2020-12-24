package com.hbhb.cw.fundcenter.rpc;

import com.hbhb.cw.systemcenter.api.UserApi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${provider.system-center}", url = "${feign-url}", contextId = "UserApi", path = "user")
public interface UserApiExp extends UserApi {

}
