package com.urlShortner.persistence.URL;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface URLRepository extends ReactiveCrudRepository<URL, UUID> {
    Mono<URL> findByLongUrl(final String longUrl);

    Mono<URL> findByShortUrl(final String shortUrl);

    @Query("Select count(*) from URL WHERE longUrl = :longUrl")
    Mono<Integer> findLongUrlCount(final String longUrl);

    @Query("Select count(*) from URL WHERE shortUrl = :shortUrl")
    Mono<Integer> findShortUrlCount(final String shortUrl);
}