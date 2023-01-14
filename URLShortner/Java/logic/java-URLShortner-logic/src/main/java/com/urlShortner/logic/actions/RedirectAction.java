package com.urlShortner.logic.actions;

import com.urlShortner.logic.constants.ShortURLConstants;
import com.urlShortner.logic.service.RedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Log
@Component
@AllArgsConstructor
public class RedirectAction {
    private final RedirectService redirectService;

    public Mono<String> redirect(final String shortUrl, final String requester) {
        log.info("Redirecting for Short Url " + shortUrl);
        if (Objects.nonNull(shortUrl) && shortUrl.trim().length() > 1) {
            return redirectService.redirect(shortUrl, requester);
        } else {
            return Mono.just(ShortURLConstants.FAILED);
        }
    }
}