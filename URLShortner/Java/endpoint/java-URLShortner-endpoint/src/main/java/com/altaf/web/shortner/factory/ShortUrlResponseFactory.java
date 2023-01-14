package com.altaf.web.shortner.factory;

import com.altaf.web.shortner.model.ShortUrlResponse;
import org.springframework.lang.NonNull;

public class ShortUrlResponseFactory {
    private ShortUrlResponseFactory() {
    }

    @NonNull
    public static ShortUrlResponse createShortUrlResponse(final String shortUrl) {
        final ShortUrlResponse response = new ShortUrlResponse();
        response.setShortUrl(shortUrl);
        return response;
    }
}
