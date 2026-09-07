package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerService {

	@Autowired
	HelloWorldSpringCloudLoadBalancerService2 helloWorldSpringCloudLoadBalancerService_2;
	
	@RequestMapping(value = "/getName", method = RequestMethod.GET, produces = "application/json")
	public String helloWorld()

	{
		String res = helloWorldSpringCloudLoadBalancerService_2.helloWorld();
		System.out.println("res:" + res);
		String tmp = "abbiamo chiamato " + res;
		return tmp;
	}
}
