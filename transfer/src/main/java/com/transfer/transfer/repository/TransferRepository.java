package com.transfer.transfer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.transfer.transfer.entity.Transfer;


@Repository
public interface TransferRepository extends ReactiveMongoRepository<Transfer,String>{

}
