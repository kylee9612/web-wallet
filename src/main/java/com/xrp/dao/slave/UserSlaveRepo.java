package com.xrp.dao.slave;

import com.xrp.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSlaveRepo extends JpaRepository<User, Long> {
}
