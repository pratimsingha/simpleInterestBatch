package com.batch.simpleInterestBatch.chunk;

import com.batch.simpleInterestBatch.filemodels.FileModel;
import com.batch.simpleInterestBatch.utils.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

public class CustomReader implements StepExecutionListener,ItemReader<FileModel> {

    @Autowired
    private Environment environment;

    private FlatFileItemReader<FileModel> fileModelFlatFileItemReader;

    private ExecutionContext executionContext;

    @Override
    public FileModel read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Reader");
        FileModel record = fileModelFlatFileItemReader.read();
        return record;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("BeforeReader");

        executionContext = new ExecutionContext();
        fileModelFlatFileItemReader = new FlatFileItemReader<FileModel>();
        String filePath = FileUtils.getFilePath(environment);
        fileModelFlatFileItemReader.setResource(new FileSystemResource(filePath));
        fileModelFlatFileItemReader.setLineMapper(lineMapper());
        fileModelFlatFileItemReader.setLinesToSkip(1);
        fileModelFlatFileItemReader.setEncoding("UTF-8");
        fileModelFlatFileItemReader.open(executionContext);
    }

    private LineMapper<FileModel> lineMapper() {
        DefaultLineMapper<FileModel> mapper = new DefaultLineMapper<>();
        mapper.setFieldSetMapper((fieldSet -> new FileModel(fieldSet.readDouble(0),fieldSet.readDouble(1),fieldSet.readDouble(2))));
        mapper.setLineTokenizer(lineTokenizer());
        return mapper;
    }

    private DelimitedLineTokenizer lineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer("|");
        tokenizer.setNames(new String[]{"Principal","Rate","Time"});
        tokenizer.setStrict(false);
        return tokenizer;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("AfterReader");
        return null;
    }
}
