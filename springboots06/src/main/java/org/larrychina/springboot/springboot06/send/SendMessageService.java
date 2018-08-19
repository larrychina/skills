package org.larrychina.springboot.springboot06.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    @Autowired
    private JmsTemplate jmsTemplate ;

    @Value("${spring.activemq.destination}")
    private String destination ;


    public void sendMessage(String msg){
        jmsTemplate.send(destination,session -> session.createTextMessage(msg));
    }

}
