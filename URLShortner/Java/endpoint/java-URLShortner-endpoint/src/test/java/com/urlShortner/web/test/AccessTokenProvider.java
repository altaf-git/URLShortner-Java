package com.urlShortner.web.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log
public class AccessTokenProvider {

    private static final String KEYCLOAK_URL = "http://127.0.0.1:8080";
    private static final String TOKEN_URL = "/realms/realmaltaf/protocol/openid-connect/token";
    private static final String GRANT_TYPE = "password";
    private static final String KEYCLOAK_CLIENT_ID = "spring-keycloak";

    private static Mono<Throwable> clientError(final ClientResponse clientResponse) {
        log.info("Error seen " + clientResponse.statusCode() + " reason " + clientResponse.statusCode().getReasonPhrase());
        return Mono.just(new HttpClientErrorException(clientResponse.statusCode()));
    }

    protected String getAccessToken(final String username, final String password) {
        log.info("We will be asking for access token in local Keycloak for " + username);
        final WebClient webClient = WebClient
                .builder()
                .baseUrl(KEYCLOAK_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        return webClient.post()
                .uri(TOKEN_URL)
                .body(BodyInserters.fromFormData("username", username)
                        .with("password", password)
                        .with("grant_type", GRANT_TYPE)
                        .with("client_id", KEYCLOAK_CLIENT_ID)
                )
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, AccessTokenProvider::clientError)
                .bodyToMono(TokenResponse.class)
                .log()
                .map(TokenResponse::getAccessToken)
                .block();
    }

    @Data
    static class TokenResponse {
        @JsonProperty("access_token")
        String accessToken;
    }
}
