package com.xrp.dao;


import com.xrp.model.vo.XrpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrpRepository extends JpaRepository<XrpAccount, Long> {

}
