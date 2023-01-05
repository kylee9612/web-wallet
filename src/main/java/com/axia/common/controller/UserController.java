package com.axia.common.controller;

import com.axia.model.vo.User;
import com.axia.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public User generateUser(){
        return userService.generateUser();
    }
}
