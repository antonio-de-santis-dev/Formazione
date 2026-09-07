package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloCtr {

	@Value("${server.instance.id}")
	String instanceId;

	@RequestMapping(value = "/ciao", method = RequestMethod.GET, produces = "application/json")
	public String helloWorld()

	{
		//java -Dserver.instance.id=1 -Dserver.port=8081 -jar HelloWorldSpringCloudLoadBalancerService2-0.0.1-SNAPSHOT.jar
		//java -Dserver.instance.id=2 -Dserver.port=8082 -jar HelloWorldSpringCloudLoadBalancerService2-0.0.1-SNAPSHOT.jar		
				
		System.out.println("instanceId:" + instanceId);
		String tmp = "ciao da " + instanceId;
		return tmp;
	}

}
