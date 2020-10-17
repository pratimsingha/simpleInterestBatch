package com.batch.simpleInterestBatch.step;

import com.batch.simpleInterestBatch.chunk.CustomProcessor;
import com.batch.simpleInterestBatch.chunk.CustomReader;
import com.batch.simpleInterestBatch.chunk.CustomWriter;
import com.batch.simpleInterestBatch.entities.Interest;
import com.batch.simpleInterestBatch.filemodels.FileModel;
//import com.batch.simpleInterestBatch.model.InterestModel;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadDataStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean(name="stepToReadDataFromFile")
    public Step stepToReadDataFromFile(){
        return stepBuilderFactory
                .get("stepToReadDataFromFile")
                .<FileModel, Interest>chunk(100)
                .reader(customReader())
                .processor(customProcessor())
                .writer(customWriter())
                .build();
    }

    @Bean
    public ItemReader<FileModel> customReader(){
        return new CustomReader();
    }

    @Bean
    public ItemProcessor<FileModel,Interest> customProcessor(){
        return new CustomProcessor();
    }

    @Bean
    public ItemWriter<Interest> customWriter(){
        return new CustomWriter();
    }
}
