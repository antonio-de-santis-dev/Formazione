package com;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	 @KafkaListener(topics = "my-topic", groupId = "my-consumer-group")
    public void listen(String message) {
    	System.out.println("messaggio ricevuto "  + message);
   }
}
