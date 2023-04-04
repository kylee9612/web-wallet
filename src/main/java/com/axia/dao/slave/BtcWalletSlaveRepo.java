package com.axia.dao.slave;

import com.axia.model.vo.BtcWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtcWalletSlaveRepo extends JpaRepository<BtcWallet, Long> {
}
