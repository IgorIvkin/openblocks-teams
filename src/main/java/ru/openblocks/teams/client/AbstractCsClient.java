package ru.openblocks.teams.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public abstract class AbstractCsClient {

    protected static final String BEARER = "Bearer ";

    protected <T> HttpEntity<T> getBasicTypedHttpRequest(String token,
                                                         T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + token);
        return new HttpEntity<>(request, headers);
    }

    protected HttpEntity<Void> getBasicHttpRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + token);
        return new HttpEntity<>(headers);
    }
}
