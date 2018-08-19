package org.larrychina.springboot.springboot06;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.activemq.util.TimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.larrychina.springboot.springboot06.send.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot06ApplicationTests {


    @Autowired
    SendMessageService sendMessageService ;
    @Test
    public void contextLoads() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            sendMessageService.sendMessage("this is a test message!-----");
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
