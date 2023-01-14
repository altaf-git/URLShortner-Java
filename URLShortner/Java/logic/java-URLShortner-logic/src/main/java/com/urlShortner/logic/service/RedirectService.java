package com.urlShortner.logic.service;

import com.urlShortner.persistence.URL.URL;
import com.urlShortner.persistence.URL.URLRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
@Log
public class RedirectService {

    private final URLRepository urlRepository;

    private Mono<Integer> findShortURLCount(final String url) {
        return urlRepository.findShortUrlCount(url);
    }

    private Mono<URL> findShortURL(final String url) {
        return urlRepository.findByShortUrl(url);
    }

    private String trimUrl(final String url) {
        String[] st = url.split("/");
        return st[st.length - 1];
    }


    public Mono<String> redirect(final String shortUrl, final String requester) {
        final String trimmedUrl = trimUrl(shortUrl);
        log.info("Trimmed Url is " + trimmedUrl);
        return findShortURLCount(trimmedUrl)
                .flatMap(count -> {
                    if (count > 0) {
                        return findShortURL(trimmedUrl)
                                .map(URL::longUrl);
                    } else {
                        return Mono.just("");
                    }
                });
    }
}
