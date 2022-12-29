//package com.xrp.config;
//
//import com.xrp.util.ReplicationRoutingDataSource;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.xrp.dao")
//public class DataSourceConfig {
//
//    @Autowired
//    public EntityManagerFactory emf;
//
//    private static final String MASTER_DATASOURCE = "masterDataSource";
//    private static final String SLAVE_DATASOURCE = "slaveDataSource";
//    @Bean(MASTER_DATASOURCE)
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    public DataSource masterDataSource(){
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @Bean(SLAVE_DATASOURCE)
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource slaveDataSource(){
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @DependsOn({MASTER_DATASOURCE,SLAVE_DATASOURCE})
//    public DataSource routingDataSource(@Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
//                                        @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource){
//        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
//        Map<Object, Object> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("master",masterDataSource);
//        dataSourceMap.put("slave",slaveDataSource);
//        routingDataSource.setTargetDataSources(dataSourceMap);
//        routingDataSource.setDefaultTargetDataSource(masterDataSource);
//
//        return routingDataSource;
//    }
//
//    @Bean
//    @DependsOn("routingDataSource")
//    public LazyConnectionDataSourceProxy dataSource(DataSource routingDataSource){
//        return new LazyConnectionDataSourceProxy(routingDataSource);
//    }
//}
