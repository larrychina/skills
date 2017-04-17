package org.larry.springboot.web;

import org.larry.springboot.entity.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Larry on 2017/3/31.
 */
@RestController
public class UserController {
    private final static AtomicInteger count = new AtomicInteger(1) ;

    @RequestMapping("user")
    public User addUser(@Validated User user, BindingResult result){
        if(result.hasErrors()){
            System.out.println(result.getFieldError().getDefaultMessage());
            return  null ;
        }
        user.setId(count.incrementAndGet());
        return user ;
    }

}
