package com.axia.dao.slave;

import com.axia.model.vo.XrpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface XrpAccountSlaveRepo extends JpaRepository<XrpAccount, Integer> {
    Optional<XrpAccount> findByDestination(int destination);
}
