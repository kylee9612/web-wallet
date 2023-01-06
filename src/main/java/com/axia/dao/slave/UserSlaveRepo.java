package com.axia.dao.slave;

import com.axia.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSlaveRepo extends JpaRepository<User, Integer> {
}
