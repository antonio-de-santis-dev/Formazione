package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
	    "server.instance.id=2",
	})
class HelloWorldSpringCloudLoadBalancerService1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
