package com.xrp.dao.slave;

import com.xrp.model.vo.XrpWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrpWalletSlaveRepo extends JpaRepository<XrpWallet, String> {
}
