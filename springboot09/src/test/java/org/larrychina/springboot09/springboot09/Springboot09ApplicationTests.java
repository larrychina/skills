package org.larrychina.springboot09.springboot09;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:configprops-test.properties")
public class Springboot09ApplicationTests {


    @Autowired
    private ConfigProperties configProperties ;
    @Test
    public void contextLoads() {
    }


    @Test
    public void testProperties(){
        assertEquals(9000,configProperties.getPort()) ;
    }

    @Test
    public void testObjectProperties(){
        assertEquals("SHA1",configProperties.getCredentials().getAuthMethod()) ;
    }


    @Test
    public void testList(){
        assertEquals(2,configProperties.getDefaultRecipients().size()) ;
    }

    @Test
    public void testMap(){
        assertEquals("true",configProperties.getAdditionalHeaders().get("redelivery")) ;
    }
}
