package com.example.demo;


import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//	   Function<PredicateSpec,Buildable<Route>> f = 
//			   p->p.path("/get")
//			   .filters(h->h.addRequestHeader("MioHeader", "ProvaHeader")
//			   .addRequestParameter("MioParam", "ProvaParametro"))
//			   .uri("http://httpbin.org:80");
		
		
		return builder.routes()
	      .route(p->p.path("/get")
				   .filters(h->h.addRequestHeader("MioHeader", "ProvaHeader")
						   .addRequestParameter("MioParam", "ProvaParametro"))
						   .uri("http://httpbin.org:80"))
		  .route(p->p.path("/api/**").uri("lb://HELLOWORLDSPRINGCLOUDEUREKACLIENT-2"))
	      .build();

	}
	
	
//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//	   Function<PredicateSpec,Buildable<Route>> f = 
//			   p->p.path("/get")
//			   .filters(h->h.addRequestHeader("MyHeader", "ProvaHeader")
//					   .addRequestParameter("MyParam", "ProvaParametro"))
//			   .uri("http://httpbin.org:80")
//			   ;
//		
//		
//		return builder.routes()
//	      .route(f)
//	     .route(p->p.path("/api/**").uri("lb://HELLOWORLDSPRINGCLOUDEUREKACLIENT-2"))
//	      .build();
//	}	
	
	
}
