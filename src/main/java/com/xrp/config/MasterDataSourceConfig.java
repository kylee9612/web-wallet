package com.xrp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(basePackages = "com.xrp.dao.master")
public class MasterDataSourceConfig {

    @Autowired
    private Environment env;
    private JpaProperties jpaProperties;
    private HibernateProperties hibernateProperties;

//    public MasterDataSourceConfig(JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
//        this.jpaProperties = jpaProperties;
//        this.hibernateProperties = hibernateProperties;
//    }
//    @Primary
//    @PostConstruct
//    public void setProperties(){
//        JpaProperties jpaProperties = new JpaProperties();
//        jpaProperties.setDatabase(Database.valueOf(env.getProperty("spring.jpa.database")));
//        jpaProperties.setOpenInView(Boolean.valueOf(env.getProperty("spring.jpa.open-in-view")));
//        jpaProperties.setShowSql(Boolean.parseBoolean(env.getProperty("spring.jpa.show-sql")));
//    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties(){
        this.jpaProperties = new JpaProperties();
        return jpaProperties;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setUsername();
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setUrl(env.getProperty("spring.datasource.master.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.master.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.master.password"));
//        return dataSource;
//        DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        System.out.println(dataSource);
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        var properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(masterDataSource())
                .properties(properties)
                .packages("com.xrp.dao.model")
                .persistenceUnit("master")
                .build();
    }

    @Bean
    @Primary
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
    }
}
