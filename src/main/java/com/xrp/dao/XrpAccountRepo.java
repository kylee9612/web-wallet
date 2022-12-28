package com.xrp.dao;


import com.xrp.model.vo.XrpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface XrpAccountRepo extends JpaRepository<XrpAccount, Long> {
    Optional<XrpAccount> findFirstByAddressAndDestination(@NonNull String address, @NonNull int destination);

}
