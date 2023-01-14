package com.altaf.web.shortner.factory;

import com.altaf.web.shortner.model.RedirectResponse;
import org.springframework.lang.NonNull;

public class RedirectResponseFactory {
    private RedirectResponseFactory() {
    }

    @NonNull
    public static RedirectResponse createRedirectResponse(final String longUrl) {
        final RedirectResponse response = new RedirectResponse();
        response.setOriginalUrl(longUrl);
        return response;
    }
}
