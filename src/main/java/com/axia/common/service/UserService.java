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
        long mbIdx = (long) (Math.random() * 1000000);
        User user;
        while (userSlaveRepo.findById(mbIdx).isPresent()) {
            mbIdx = (long) (Math.random() * 1000000);
        }
        user = new User(mbIdx);
        userRepo.save(user);
        return user;
    }
}
