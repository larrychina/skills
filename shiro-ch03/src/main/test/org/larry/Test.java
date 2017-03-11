package org.larry;

import org.junit.runner.RunWith;
import org.larry.shiro.entity.User;
import org.larry.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Larry on 2017/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:spring-*.xml"})
public class Test {

    @Autowired
    private UserService userService ;
    @org.junit.Test
    public void test1(){
        User user = new User() ;
        user.setLocked(false);
        user.setUsername("li");
        user.setPassword("1234");
        user.setOrganizationId(1l);
        userService.createUser(user) ;

    }
}
