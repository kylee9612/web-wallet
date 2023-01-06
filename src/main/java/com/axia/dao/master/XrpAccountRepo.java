package com.axia.dao.master;


import com.axia.model.vo.XrpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface XrpAccountRepo extends JpaRepository<XrpAccount, Integer> {
    Optional<XrpAccount> findFirstByAddressAndDestination(@NonNull String address, @NonNull int destination);

}
