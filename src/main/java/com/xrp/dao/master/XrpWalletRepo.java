package com.xrp.dao.master;

import com.xrp.model.vo.XrpWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrpWalletRepo extends JpaRepository<XrpWallet, String> {
}
