package com.valueIt.demo.configurations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "studentsEntityManagerFactory",basePackages = "com.valueIt.demo.repos.students")
@EnableTransactionManagement
public class StudentsDBConfig {

    @Bean("studentsDatasource")
    @ConfigurationProperties(prefix = "spring.students.datasource")
    public DataSource dataSource()
    {
        return DataSourceBuilder.create().build();
    }
    @Bean("studentsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("studentsDatasource") DataSource dataSource)
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return builder.dataSource(dataSource).properties(properties).packages("com.valueIt.demo.entities.students").persistenceUnit("Course").build();
    }
    @Bean(name="studentsTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("studentsEntityManagerFactory") EntityManagerFactory entityManagerFactory
    )
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
