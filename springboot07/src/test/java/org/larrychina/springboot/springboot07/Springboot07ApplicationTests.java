package org.larrychina.springboot.springboot07;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.larrychina.springboot.springboot07.dao.SpUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot07ApplicationTests {

    @Autowired
    SpUserDao spUserDao ;

    @Test
    public void contextLoads() {
        List<Map<String, Object>> spUserEntities = spUserDao.queryAll();
        System.out.println(spUserEntities);
    }

}
