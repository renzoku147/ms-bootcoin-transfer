package com.spring.msbootcointransfer.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring.msbootcointransfer.handler.BootCoinTransferHandler;

@Component
public class BootCoinTransferRouter {

	@Bean
    public RouterFunction<ServerResponse> routes (BootCoinTransferHandler handler) {
        return route(GET("/list"), handler::findAll)
        		.andRoute(POST("/create"), handler::create)
        		.andRoute(DELETE("/delete/{id}"), handler::delete);
    }
}
