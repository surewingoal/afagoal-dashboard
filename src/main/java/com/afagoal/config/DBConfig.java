package com.afagoal.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * Created by BaoCai on 18/1/21.
 * Description:
 */
@Configuration
public class DBConfig {

    @Bean
    @Primary
    public PlatformTransactionManager dashBoardTransactionManager() {
        return new JpaTransactionManager(dashboardEntityManagerFactory().getObject());
    }

    @Bean
    @Primary
    public EntityManager dashBoardEntityManager() {
        return dashboardEntityManagerFactory().getObject().createEntityManager();
    }

    @Bean(name = "dashBoardDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.dashboard")
    public DataSource dashBoardDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean dashboardEntityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dashBoardDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(
                "com.afagoal"
        );
        Map<String, Object> map = new HashMap<>();
        map.put("hibernate.physical_naming_strategy", new SpringPhysicalNamingStrategy());
        factoryBean.setJpaPropertyMap(map);
        return factoryBean;
    }

}
