package org.larry.springboot.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import org.larry.springboot.dao.UserDao;
import org.larry.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Larry on 2017/3/31.
 */
@RestController
public class UserController {

    @Autowired
    private UserDao userDao ;

    @ApiOperation(value="Get all users",notes="requires noting")
    @RequestMapping("/users")
    public List<User> users(){
        List<User> users = userDao.queryAll() ;
        return users ;
    }
}
