package com.axia.common.service;

import com.axia.dao.master.UserRepo;
import com.axia.dao.slave.UserSlaveRepo;
import com.axia.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserSlaveRepo userSlaveRepo;

    public User generateUser() {
        int mbIdx = (int) (Math.random() * 1000000);
        User user;
        while (userRepo.findById(mbIdx).isPresent()) {
            mbIdx = (int) (Math.random() * 1000000);
        }
        user = new User(mbIdx);
        userRepo.save(user);
        return user;
    }
}
