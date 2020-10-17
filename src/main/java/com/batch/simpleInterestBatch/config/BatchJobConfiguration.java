package com.batch.simpleInterestBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private BatchListener batchListener;

    @Autowired
    private Step stepToReadDataFromFile;

    @Bean(name="simpleInterestJob")
    @Primary
    public Job simpleInterestJob(){
        return jobBuilderFactory
                .get("simpleInterestJob")
                .incrementer(new RunIdIncrementer())
                .listener(batchListener)
                .start(stepToReadDataFromFile)
                .build();
    }


}
