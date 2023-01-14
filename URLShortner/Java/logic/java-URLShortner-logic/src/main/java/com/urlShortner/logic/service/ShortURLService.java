package com.urlShortner.logic.service;

import com.urlShortner.logic.helper.Helper;
import com.urlShortner.persistence.URL.URL;
import com.urlShortner.persistence.URL.URLRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Log
public class ShortURLService {

    private final URLRepository urlRepository;

    public Mono<URL> save(final URL url) {
        return urlRepository.save(url)
                .doOnEach(x -> log.info("Record Saved"));
    }

    public Mono<Integer> findLongURLCount(final String url) {
        return urlRepository.findLongUrlCount(url);
    }

    public Mono<URL> findLongURL(final String url) {
        return urlRepository.findByLongUrl(url);
    }

    public Mono<String> shortURL(final String longUrl, final String requester) {
        return findLongURLCount(longUrl)
                .flatMap(count -> {
                    log.info("The Count is " + count);
                    if (count > 0) {
                        return findLongURL(longUrl)
                                .map(URL::shortUrl);
                    } else {
                        return new Helper().shortning(longUrl, requester, urlRepository);
                    }
                });
    }
}
