package com.example.demo;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "HelloWorldSpringCloudLoadBalancerService2")
public interface HelloWorldSpringCloudLoadBalancerService2 {

	@RequestMapping(value = "/api/ciao", method = RequestMethod.GET, produces = "application/json")
	public String helloWorld();

}
