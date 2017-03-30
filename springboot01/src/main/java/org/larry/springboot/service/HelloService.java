package org.larry.springboot.service;

import org.larry.springboot.dao.UserDao;
import org.larry.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Larry on 2017/3/29.
 */
@Service
public class HelloService {

    @Autowired
    private UserDao userDao ;

    public String getJson(){
        return "hello spring boot!" ;
    }

    public List<User> getAllUser(){
        return userDao.findAll() ;
    }


}
