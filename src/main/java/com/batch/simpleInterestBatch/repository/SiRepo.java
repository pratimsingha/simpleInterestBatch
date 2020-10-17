package com.batch.simpleInterestBatch.repository;

import com.batch.simpleInterestBatch.entities.Interest;
//import com.batch.simpleInterestBatch.model.InterestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiRepo extends JpaRepository<Interest,Integer> {
}
