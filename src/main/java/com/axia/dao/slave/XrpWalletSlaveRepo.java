package com.axia.dao.slave;

import com.axia.model.vo.XrpWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrpWalletSlaveRepo extends JpaRepository<XrpWallet, String> {
}
