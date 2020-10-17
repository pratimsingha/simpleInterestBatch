package com.batch.simpleInterestBatch.databaseConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "siEntityManagerFactory",
transactionManagerRef = "siTransactionManager",
basePackages = "com.batch.simpleInterestBatch.repository")
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Bean("siEntityManagerFactory")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public LocalContainerEntityManagerFactoryBean getSiEntityManagerFactory(
            EntityManagerFactoryBuilder factoryBuilder) throws IOException{
        EntityManagerFactoryBuilder.Builder builder = factoryBuilder.dataSource(getDatasource());
        Map<String,?> hibernateProperties = readHibernateProp("hibernate.properties");
        if (hibernateProperties != null){
            builder.properties(hibernateProperties);
        }
        return builder.packages("com.batch.simpleInterestBatch.entities").build();
    }

    private Map<String,?> readHibernateProp(String s) throws IOException {
        Resource resource = new ClassPathResource(s);
        if (!resource.exists()) return null;
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        return properties.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
    }

    @Bean(name = "siDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource getDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.second-datasource.driverClassName"));
        dataSource.setUrl(environment.getProperty("spring.second-datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.second-datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.second-datasource.password"));

        return dataSource;
    }

    @Bean("siTransactionManager")
    public PlatformTransactionManager getTransactionManager(
            @Qualifier("siEntityManagerFactory")EntityManagerFactory entityManagerFactory
            ){
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
        return transactionManager;

    }
}
