package com.spring.msbootcointransfer.service;


import com.spring.msbootcointransfer.entity.BootCoin;
import com.spring.msbootcointransfer.entity.BootCoinRequest;
import com.spring.msbootcointransfer.entity.BootCoinTransfer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootCoinTransferService {
	Mono<BootCoinTransfer> create(BootCoinTransfer t);

    Flux<BootCoinTransfer> findAll();

    Mono<BootCoinTransfer> findById(String id);
    
    Mono<BootCoinTransfer> update(BootCoinTransfer t);
    
    Mono<Boolean> delete(String t);
    
    Mono<BootCoinRequest> findByIdBootCoinRequest(String id);
    
    Mono<BootCoin> findByIdBootCoin(String id);
}
