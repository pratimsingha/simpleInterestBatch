package com.batch.simpleInterestBatch.chunk;

import com.batch.simpleInterestBatch.entities.Interest;
import com.batch.simpleInterestBatch.filemodels.FileModel;
//import com.batch.simpleInterestBatch.model.InterestModel;
import com.batch.simpleInterestBatch.filemodels.SiRestCall;
import com.batch.simpleInterestBatch.utils.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public class CustomProcessor implements StepExecutionListener, ItemProcessor<FileModel, Interest> {

    RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("BeforeProcessor");
        restTemplate = new RestTemplate();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("afterProcessor");
        return null;
    }

    @Override
    public Interest process(FileModel fileModel) throws Exception {
        System.out.println("Processor");
        String uri = FileUtils.getUri(environment);
        StringBuilder stringBuilder = new StringBuilder(uri);
        stringBuilder.append(fileModel.getPrincipal())
                .append("/")
                .append(fileModel.getRate())
                .append("/")
                .append(fileModel.getTime());
        uri = stringBuilder.toString();
        SiRestCall siRestCall = restTemplate.getForObject(uri,SiRestCall.class);
        Interest interestModel = new Interest(fileModel.getPrincipal(),fileModel.getRate(),fileModel.getTime(),siRestCall.getResult());

        return interestModel;
    }
}
