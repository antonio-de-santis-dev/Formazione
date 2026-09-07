package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;



@Component
public class ArtInfoContributor implements InfoContributor 
{
		@Override
	public void contribute(Builder builder) 
	{
		
		
		Map<String, Object> ArtMap = new HashMap<String, Object>();
		ArtMap.put("giorni liberi", 2);
	        
	    builder.withDetail("microservizio-info", ArtMap);
	}

}
