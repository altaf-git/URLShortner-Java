package com.altaf.web.shortner.router;

import com.altaf.web.shortner.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Router {
    public static final String REDIRECT = "/";
    public static final String URL_SHORTNER = "/shortURL/v1/shortner";
    public static final String HEALTH = "/shortURL/v1/echo";

    @Bean
    public RouterFunction<ServerResponse> shortURLRoutes(final Handler handler) {
        return RouterFunctions
                .route(POST(URL_SHORTNER).and(accept(MediaType.APPLICATION_JSON)), handler::shortUrl)
                .andRoute(GET(REDIRECT).and(accept(MediaType.APPLICATION_JSON)), handler::redirect)
                .andRoute(GET(HEALTH).and(accept(MediaType.APPLICATION_JSON)), handler::health);
    }
}
