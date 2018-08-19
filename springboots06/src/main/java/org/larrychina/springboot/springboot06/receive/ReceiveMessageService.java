package org.larrychina.springboot.springboot06.receive;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessageService {

    @JmsListener(destination = "${spring.activemq.destination}", containerFactory="myFactory")
    public void processMessage(String content) {

        System.out.println("**************************************************************************");
        System.out.println(content);
        System.out.println("**************************************************************************");

    }
}
