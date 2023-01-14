package com.altaf.web.shortner.handler;

import com.altaf.web.shortner.model.ShortUrlRequest;
import com.altaf.web.shortner.service.ShortUrlService;
import com.urlShortner.web.webflux.handler.AbstractBaseHandler;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Log
@AllArgsConstructor
public class Handler extends AbstractBaseHandler {

    private final ShortUrlService service;

    public Mono<ServerResponse> redirect(final ServerRequest request) {
        return service.redirect(request.path())
                .flatMap(url -> {
                    if (url.equals("")) {
                        return ServerResponse.badRequest().build();
                    } else {
                        final URI uri;
                        try {
                            uri = new URI(url);
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                        return ServerResponse.permanentRedirect(uri).build();
                    }
                });
    }

    public Mono<ServerResponse> shortUrl(final ServerRequest request) {
        return super.processRequestForServerResponseMono(request, ShortUrlRequest.class, service::shortUrl);
    }
}
