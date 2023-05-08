package com.axia.dao.slave.config;

import com.axia.model.vo.NodeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeConfigSlaveRepo extends JpaRepository<NodeConfig, String> {
}
