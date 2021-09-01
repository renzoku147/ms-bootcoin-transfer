package com.spring.msbootcointransfer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.spring.msbootcointransfer.entity.BootCoin;
import com.spring.msbootcointransfer.entity.BootCoinRequest;
import com.spring.msbootcointransfer.entity.BootCoinTransfer;
import com.spring.msbootcointransfer.repository.BootCoinTransferRepository;
import com.spring.msbootcointransfer.service.BootCoinTransferService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BootCoinTransferServiceImpl implements BootCoinTransferService{

	@Autowired
	BootCoinTransferRepository bootCoinTransferRepository;
	
	WebClient webClientBootcoinRequest = WebClient.create("http://localhost:8887/ms-bootcoin-request");
	
	WebClient webClientBootcoin = WebClient.create("http://localhost:8887/ms-bootcoin");
	
	@Override
	public Mono<BootCoinTransfer> create(BootCoinTransfer t) {
		// TODO Auto-generated method stub
		return bootCoinTransferRepository.save(t);
	}

	@Override
	public Flux<BootCoinTransfer> findAll() {
		// TODO Auto-generated method stub
		return bootCoinTransferRepository.findAll();
	}

	@Override
	public Mono<BootCoinTransfer> findById(String id) {
		// TODO Auto-generated method stub
		return bootCoinTransferRepository.findById(id);
	}

	@Override
	public Mono<BootCoinTransfer> update(BootCoinTransfer t) {
		// TODO Auto-generated method stub
		return bootCoinTransferRepository.save(t);
	}

	@Override
	public Mono<Boolean> delete(String t) {
        return bootCoinTransferRepository.findById(t)
                .flatMap(
                		bootCoinTransfer -> bootCoinTransferRepository.delete(bootCoinTransfer)
                                .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }

	@Override
	public Mono<BootCoinRequest> findByIdBootCoinRequest(String id) {
		// TODO Auto-generated method stub
		return webClientBootcoinRequest.get().uri("/findById/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BootCoinRequest .class);
	}

	@Override
	public Mono<BootCoin> findByIdBootCoin(String id) {
		// TODO Auto-generated method stub
		return webClientBootcoin.get().uri("/findById/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BootCoin .class);
	}

}
