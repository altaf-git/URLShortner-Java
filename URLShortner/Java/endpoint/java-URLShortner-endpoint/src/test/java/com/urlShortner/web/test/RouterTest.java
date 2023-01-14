package com.urlShortner.web.test;

import com.altaf.web.shortner.model.ShortUrlRequest;
import com.altaf.web.shortner.model.ShortUrlResponse;
import com.altaf.web.shortner.router.Router;
import com.urlShortner.web.webflux.launch.WebFluxApplication;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import static com.urlShortner.logic.constants.ShortURLConstants.FAILED;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebFluxApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ComponentScan(
        basePackages = {"com.urlShortner"}
)
@SpringBootApplication
@EnableWebFlux
@Log
public class RouterTest extends AccessTokenProvider {
    private static final String BASE_URL = "http://127.0.0.1:18082";
    private static final String TEST_USER = "altaf";
    private static final String TEST_USER_PASSWORD = "password123";
    private static final String TEST_URL = "https://www.youtube.com/";
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void beforeEach() {
        String baseUri = "http://localhost:" + "18082";
        this.webTestClient = WebTestClient.bindToServer().baseUrl(baseUri).build();
    }

    @Test
    void testThat401StatusIsReturnedWhenNoUserCredentialsInHeader() {
        webTestClient
                .post().uri(Router.URL_SHORTNER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(401);
    }

    @Test
    void testForIncorrectPassword() {
        try {
            final String token = getAccessToken("prashant", "incorrectPassword");
            log.info("Token " + token);
            Assertions.assertTrue(token.length() > 0, "We shouldn't have reached here");
        } catch (HttpClientErrorException e) {
            log.info("Test that incorrect password is working.." + e.getMessage());
        }
    }

    @Test
    void testThatATokenWorks() {
        final String token = getAccessToken("altaf", "password123");
        log.info("Token " + token);

        webTestClient
                .post().uri(Router.URL_SHORTNER)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isEqualTo(422);
    }

    @Test
    void testUrlShortner() {
        final WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();
        final String token = getAccessToken(TEST_USER, TEST_USER_PASSWORD);
        log.info("Token " + token);
        final ShortUrlRequest createRequest = new ShortUrlRequest();
        createRequest.setLongUrl(TEST_URL);
        final ShortUrlResponse shortUrlResponse = makeAPICall(webClient, token, createRequest, Router.URL_SHORTNER, ShortUrlResponse.class);
        Assertions.assertEquals(FAILED, shortUrlResponse.getShortUrl());

//        webTestClient.post().uri(Router.URL_SHORTNER)
//                .accept(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                .bodyValue(createRequest)
//                .exchange()
//                .expectStatus().is2xxSuccessful()
//                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    private <T, U> T makeAPICall(final WebClient webClient, final String token, final U request, final String uri, final Class<T> clazz) {
        final T response = webClient
                .post().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(clazz).block();
        assert response != null;
        return response;
    }
}

