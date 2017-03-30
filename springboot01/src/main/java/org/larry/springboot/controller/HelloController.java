package org.larry.springboot.controller;

import org.larry.springboot.entity.User;
import org.larry.springboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Larry on 2017/3/28.
 */
@RestController
public class HelloController {


    @Autowired
    private HelloService helloService ;

    @RequestMapping("/users")
    List<User> home(){
        List<User> users = helloService.getAllUser() ;
        return users ;
    }

    @RequestMapping("index")
    String index(){
        return "index" ;
    }

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


}
