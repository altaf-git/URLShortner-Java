package com.altaf.web.shortner.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedirectResponse {
    private String originalUrl;
}
