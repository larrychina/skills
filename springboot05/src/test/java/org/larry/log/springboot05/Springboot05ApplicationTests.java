package org.larry.log.springboot05;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot05ApplicationTests {

    Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    @Test
    public void contextLoads() {
        logger.info("****************************info***************************************");
        logger.debug("****************************debug***************************************");
        logger.error("****************************error***************************************");
        System.out.println("hello");
        logger.info("****************************info***************************************");
        logger.debug("****************************debug***************************************");
        logger.error("****************************error***************************************");

    }

}
