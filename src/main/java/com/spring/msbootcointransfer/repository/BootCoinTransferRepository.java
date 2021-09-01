package com.spring.msbootcointransfer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.spring.msbootcointransfer.entity.BootCoinTransfer;

public interface BootCoinTransferRepository extends ReactiveMongoRepository<BootCoinTransfer, String> {

}
