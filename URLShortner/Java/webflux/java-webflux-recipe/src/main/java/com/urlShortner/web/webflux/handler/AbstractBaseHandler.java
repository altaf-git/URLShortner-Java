package com.urlShortner.web.webflux.handler;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerErrorException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Log
public abstract class AbstractBaseHandler implements HealthCheckAware {

    protected <T,R> Mono<ServerResponse> processRequestForServerResponseMono(final ServerRequest request,
                                                                              final Class<T> requestBodyClass,
                                                                              final Function<T, Mono<R>> transformer) {
        return extractBody(request, requestBodyClass)
                .flatMap(body -> transformer.apply(body)
                        .switchIfEmpty(Mono.error(new ServerErrorException("Empty Body")))
                        .map(r -> new Tuples<>(request, body, requestBodyClass, r, null, HttpStatus.I_AM_A_TEAPOT, null))
                        .flatMap(t-> Mono.just(t.serverResponse))
                );
    }
    private <T> Mono<T> extractBody(final ServerRequest request, final Class<T> requestBodyClass) {
        switch (Objects.requireNonNull(request.method())) {
            case PUT, POST -> {
                return request.bodyToMono(requestBodyClass);
            }
        }
        return extractFromRequest(request, requestBodyClass);
    }

    protected <T> Mono<T> extractFromRequest(final ServerRequest request, final Class<T> requestBodyClass) {
        return Mono.just(0)
                .then(Mono.empty());
    }

    record Tuples<T, R>(ServerRequest serverRequest, Object requestBody, Class<T> requestClazz,
                                  R internalResponse, Throwable throwable, HttpStatus responseStatus,
                                  ServerResponse serverResponse) {
    }
}