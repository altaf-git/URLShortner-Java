package com.altaf.web.shortner.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShortUrlRequest {
    private String longUrl;
}
