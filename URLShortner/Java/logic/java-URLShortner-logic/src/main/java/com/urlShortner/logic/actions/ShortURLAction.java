package com.urlShortner.logic.actions;

import com.urlShortner.logic.constants.ShortURLConstants;
import com.urlShortner.logic.service.ShortURLService;
import com.urlShortner.persistence.URL.URL;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Log
@Component
@AllArgsConstructor
public class ShortURLAction {
    private final ShortURLService shortURLService;

    public Mono<String> shortURL(final String longUrl, final String requester) {
        if (Objects.nonNull(longUrl) && longUrl.trim().length() > 1) {
            return shortURLService.shortURL(longUrl, requester)
                    .flatMap(convertedUrl -> {
                        final URL url = new URL(UUID.randomUUID(), longUrl, convertedUrl, requester, null, null, null, null, true);
                        return shortURLService.save(url)
                                .map(URL::shortUrl);
                    });
        } else {
            return Mono.just(ShortURLConstants.FAILED);
        }
    }
}