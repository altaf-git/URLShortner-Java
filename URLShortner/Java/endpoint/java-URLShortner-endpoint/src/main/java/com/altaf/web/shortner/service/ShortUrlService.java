package com.altaf.web.shortner.service;

import com.altaf.web.shortner.helper.Helper;
import com.altaf.web.shortner.model.ShortUrlRequest;
import com.altaf.web.shortner.model.ShortUrlResponse;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Log
public class ShortUrlService {
    private final Helper helper;

    public Mono<String> redirect(final String request) {
        return helper.redirect(request);
    }

    public Mono<ShortUrlResponse> shortUrl(final ShortUrlRequest request) {
        return helper.shortUrl(request);
    }
}
