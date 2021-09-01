package com.spring.msbootcointransfer.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring.msbootcointransfer.entity.BootCoinTransfer;
import com.spring.msbootcointransfer.kafka.ProductorKafka;
import com.spring.msbootcointransfer.service.BootCoinTransferService;

import reactor.core.publisher.Mono;

@Component
public class BootCoinTransferHandler {
	@Autowired
	BootCoinTransferService bootCoinTransferService;
	
	@Autowired
	ProductorKafka productorKafka;
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
        		.contentType(MediaType.APPLICATION_JSON)
        		.body(bootCoinTransferService.findAll(), BootCoinTransfer.class);
    }
	
	public Mono<ServerResponse> create(ServerRequest request) {
		Mono<BootCoinTransfer> monoBootCoint = request.bodyToMono(BootCoinTransfer.class);
		
		  return monoBootCoint.flatMap(bootcointTransfer -> {
					return bootCoinTransferService.findByIdBootCoinRequest(bootcointTransfer.getBuyer().getId())
						.flatMap(buyer -> {
							bootcointTransfer.setBuyer(buyer);
							System.err.println("Encontro BUYER");
							// COMPRADOR
							switch(buyer.getTypePaid()) {
								case YANKI: template.send("topico-everis6", buyer); break;
								case TRANSFER: 	template.send("topico-everis7", buyer); break;
							}
							return bootCoinTransferService.findByIdBootCoin(bootcointTransfer.getSeller().getId())
									.flatMap(sellerMonedero -> {
										System.err.println("Encontro SELLER");
										// SELLER
										bootcointTransfer.setSeller(sellerMonedero);
										bootcointTransfer.setBuyer(buyer);
										switch(bootcointTransfer.getTypePaid()) {
											case YANKI: template.send("topico-everis8", bootcointTransfer); break;
											case TRANSFER: template.send("topico-everis9", bootcointTransfer); break;
										}
										template.send("topico-everis10", bootcointTransfer);
										
										double vr = (Math.random()*100000);
										String cadena = String.valueOf(vr);
										bootcointTransfer.setNumberTransaction(cadena);
										
										return bootCoinTransferService.create(bootcointTransfer)
												.flatMap(bctCreated -> ServerResponse
				    													.status(HttpStatus.CREATED)
				    													.contentType(MediaType.APPLICATION_JSON)
				    													.body(fromValue(bctCreated)));
									});
						});
				});
	}
	
	public Mono<ServerResponse> delete(ServerRequest request) {
		String id = request.pathVariable("id");
		System.out.println("Esta llegando al DELETE");
        return bootCoinTransferService.delete(id)
        		.flatMap(bootCoinRequest -> ServerResponse.status(HttpStatus.OK)
								.bodyValue("BootCoin Eliminado"));
    }
}
