package com.example.demo;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerInstanceConfiguration {

	@Bean
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new HelloWorldSpringBootInstanceSupplier("HelloWorldSpringCloudLoadBalancerService_1");
    }
}
