package com.xrp.dao.slave;

import com.xrp.model.vo.XrpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrpAccountSlaveRepo extends JpaRepository<XrpAccount, Long> {
}
