package org.larry.springboot08.springboot08.controller;

import javafx.scene.paint.Color;
import org.larry.springboot08.springboot08.vo.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class MyRestController {

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public User getUsers(@PathVariable Long userId) {
        User user = new User(Color.ALICEBLUE);
        return user;
    }
}
