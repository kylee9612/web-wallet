package com.axia.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.Database;

@Configuration
public class PropertyConfig {

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public JpaProperties jpaProperties(){
        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setDatabase(Database.MYSQL);
        jpaProperties.setOpenInView(Boolean.valueOf(env.getProperty("spring.jpa.open-in-view")));
        jpaProperties.setShowSql(Boolean.parseBoolean(env.getProperty("spring.jpa.show-sql")));
        return jpaProperties;
    }

    @Bean
    @Primary
    public HibernateProperties hibernateProperties(){
        HibernateProperties hibernateProperties = new HibernateProperties();
        hibernateProperties.setDdlAuto(env.getProperty("spring.jpa.hibernate.ddl-auto"));
        hibernateProperties.setUseNewIdGeneratorMappings(Boolean.valueOf(env.getProperty("spring.jpa.hibernate.use-new-id-generator-mappings")));
        return hibernateProperties;
    }

    @Bean("masterDataSource")
    @Primary
    public HikariDataSource masterDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUsername(env.getProperty("spring.datasource.master.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.master.password"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.master.url"));
        return dataSource;
    }

    @Bean("slaveDataSource")
    public HikariDataSource slaveDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUsername(env.getProperty("spring.datasource.slave.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.slave.password"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.slave.url"));
        return dataSource;
    }
}
