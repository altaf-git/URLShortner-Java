package com.pinboxinclusion.logic.test;


import com.urlShortner.logic.service.RedirectService;
import com.urlShortner.logic.service.ShortURLService;
import com.urlShortner.persistence.URL.URL;
import com.urlShortner.persistence.URL.URLRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.UUID;

@Log
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ComponentScan(
        basePackages = {"com.urlShortner"}
)
@SpringBootApplication
@EnableWebFlux
public class ShortUrlTests {
    private static final String TEST_URL = "https://www.youtube.com/";
    @Autowired
    private URLRepository repository;

    @BeforeEach
    @Transactional
    public void updateDataBase() {
        repository.deleteAll().block();
        final URL url = new URL(UUID.randomUUID(), TEST_URL, "adasdasd", "altaf", null, null, null, null, true);
        repository.save(url).block();
    }

    @Test
    void testShortUrl() {
        final ShortURLService service = new ShortURLService(repository);
        final String url = service.shortURL(TEST_URL, "altaf").block();
        Assertions.assertNotNull(url);
        log.info("The Url is: " + url);
    }

    @Test
    void testRedirect() {
        final RedirectService service = new RedirectService(repository);
        final String url = service.redirect("https://localhost:18000/adasdasd", "altaf").block();
        Assertions.assertNotNull(url);
        log.info("The Url is: " + url);
    }
}
