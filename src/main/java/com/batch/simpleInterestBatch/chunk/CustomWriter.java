package com.batch.simpleInterestBatch.chunk;

import com.batch.simpleInterestBatch.entities.Interest;
//import com.batch.simpleInterestBatch.model.InterestModel;
import com.batch.simpleInterestBatch.repository.SiRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class CustomWriter implements ItemWriter<Interest> {

    @Autowired
    @Lazy
    private SiRepo siRepo;

    @Override
    public void write(List<? extends Interest> list) throws Exception {
        System.out.println("Writer");
        siRepo.saveAll(list);
    }
}
