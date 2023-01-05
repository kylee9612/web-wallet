package com.axia.dao.master;

import com.axia.model.vo.BtcWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtcWalletRepo extends JpaRepository<BtcWallet, Long> {
}
