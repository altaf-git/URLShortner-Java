package com.altaf.web.shortner.helper;

import com.altaf.web.shortner.factory.ShortUrlResponseFactory;
import com.altaf.web.shortner.model.ShortUrlRequest;
import com.altaf.web.shortner.model.ShortUrlResponse;
import com.urlShortner.logic.actions.RedirectAction;
import com.urlShortner.logic.actions.ShortURLAction;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.urlShortner.logic.constants.ShortURLConstants.FAILED;

@Component
@AllArgsConstructor
@Log
public class Helper {
    private final ShortURLAction shortURLAction;
    private final RedirectAction redirectAction;

    public Mono<ShortUrlResponse> shortUrl(final ShortUrlRequest request) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> shortURLAction.shortURL(request.getLongUrl(), securityContext.getAuthentication().getName())
                        .switchIfEmpty(Mono.just(FAILED)))
                .map(ShortUrlResponseFactory::createShortUrlResponse);
    }

    public Mono<String> redirect(final String request) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> redirectAction.redirect(request, securityContext.getAuthentication().getName())
                        .switchIfEmpty(Mono.just(FAILED)));
    }
}
