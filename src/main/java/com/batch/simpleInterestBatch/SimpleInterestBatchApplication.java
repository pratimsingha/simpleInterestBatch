package com.batch.simpleInterestBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableConfigurationProperties
//@EntityScan("com.batch.simpleInterestBatch.model")
@EntityScan(basePackages="com.batch.simpleInterestBatch.entities")
public class SimpleInterestBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleInterestBatchApplication.class, args);
	}

}
