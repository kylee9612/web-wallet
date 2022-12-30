package com.xrp.controller;

import com.xrp.model.vo.User;
import com.xrp.service.UserService;
import org.checkerframework.checker.units.qual.A;
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
