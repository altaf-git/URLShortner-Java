package com.urlShortner.logic.helper;

import com.urlShortner.persistence.URL.URLRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Log
public class Helper {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int BASE = ALPHABET.length();

    public static String fromBase10(int i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return "a";
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    private static int fromBase10(int i, final StringBuilder sb) {
        int rem = i % BASE;
        sb.append(ALPHABET.charAt(rem));
        return i / BASE;
    }

    public static int toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static int toBase10(char[] chars) {
        int n = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private static int toBase10(int n, int pow) {
        return n * (int) Math.pow(BASE, pow);
    }

    public Mono<String> shortningLogic(final String url) {
        final int toInt = toBase10(url);
        log.info("The converted integer is: " + toInt);
        return Mono.just(fromBase10(Math.abs(toInt)));
    }

    public Mono<String> shortning(final String longUrl, final String requester, final URLRepository repository) {
        return shortningLogic(longUrl)
                .map(convertedUrl -> {
                    log.info("Long Url is " + longUrl);
                    log.info("Converted Url is " + convertedUrl);
                    return convertedUrl;
                });
    }
}
