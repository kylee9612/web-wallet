package com.axia.dao.master.config;

import com.axia.model.vo.NodeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeConfigRepo extends JpaRepository<NodeConfig, String>{
}
