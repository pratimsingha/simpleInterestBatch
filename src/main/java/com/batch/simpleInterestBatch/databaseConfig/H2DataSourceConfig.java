package com.batch.simpleInterestBatch.databaseConfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jmx.export.MBeanExporter;
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
@EnableJpaRepositories(entityManagerFactoryRef = "batchEntityManagerFactory",
transactionManagerRef = "batchTransactionManager")
@EnableTransactionManagement
public class H2DataSourceConfig {

    @Autowired
    private Environment environment;

    public static final int MINIMUM_IDLE = 2;

    @Bean("batchEntityManagerFactory")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public LocalContainerEntityManagerFactoryBean getBatchEntityManagerFactory(
            EntityManagerFactoryBuilder factoryBuilder) throws IOException{
        EntityManagerFactoryBuilder.Builder builder = factoryBuilder.dataSource(batchDatasource());
        Map<String,?> hibernateProperties = readHibernateProp("hibernate-batch.properties");
        if (hibernateProperties != null){
            builder.properties(hibernateProperties);
        }
        return builder.packages("com.batch.simpleinterestBatch.model").build();
    }

    private Map<String,?> readHibernateProp(String s) throws IOException {
        Resource resource = new ClassPathResource(s);
        if (!resource.exists()) return null;
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        return properties.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
    }

    @Primary
    @Bean(name = "batchDataSource")
    //@ConfigurationProperties(prefix = "spring.datasource")
    public DataSource batchDatasource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getRequiredProperty("spring.datasource.url"));
        config.setUsername(environment.getProperty("spring.datasource.username"));
        config.setPassword(environment.getProperty("spring.datasource.password"));
        //config.setMinimumIdle(environment.getProperty(""),Integer.class,MINIMUM_IDLE);
        config.setRegisterMbeans(true);
        return new HikariDataSource(config);

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getProperty("spring.second-datasource.driverClassName"));
//        dataSource.setUrl(environment.getProperty("spring.second-datasource.url"));
//        dataSource.setUsername(environment.getProperty("spring.second-datasource.username"));
//        dataSource.setPassword(environment.getProperty("spring.second-datasource.password"));
//
//        return dataSource;
    }

    @Bean("batchTransactionManager")
    public PlatformTransactionManager getBatchTransactionManager(
            @Qualifier("batchEntityManagerFactory")EntityManagerFactory entityManagerFactory
            ){
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
        return transactionManager;

    }

//    @Primary
//    @Bean
//    public MBeanExporter exporter(){
//        final MBeanExporter exporter = new MBeanExporter();
//        exporter.setExcludedBeans("batchDataSource","siDataSource");
//        return exporter;
//    }
}
