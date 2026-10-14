package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCtr {

    @Autowired
    KafkaProducer producer;

    //http://localhost:8080/send?msg=ciao
    @GetMapping("/send")
    public String sendMsg(@RequestParam("msg") String message) {
        producer.sendMessage(message);
        return "Message sent to Kafka Topic";
    }
}
