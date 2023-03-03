package ru.openblocks.teams.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Этот вспомогательный класс служит для оборачивания HTTP-запроса таким образом, чтобы
 * его можно было изменять в фильтрах, к примеру, добавлять новые HTTP-заголовки.
 */
public class MutableHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new LinkedCaseInsensitiveMap<>();
    }

    public void setHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    private HttpServletRequest getServletRequest() {
        return (HttpServletRequest) getRequest();
    }

    @Override
    public String getHeader(String name) {
        return Optional.ofNullable(customHeaders.get(name))
                .orElseGet(() -> getServletRequest().getHeader(name));
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return Optional.ofNullable(customHeaders.get(name))
                .map(v -> Collections.enumeration(List.of(v)))
                .orElseGet(() -> getServletRequest().getHeaders(name));
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(
                Stream.concat(
                                customHeaders.keySet().stream(),
                                StreamSupport.stream(
                                        Spliterators.spliteratorUnknownSize(
                                                getServletRequest()
                                                        .getHeaderNames()
                                                        .asIterator(),
                                                Spliterator.ORDERED), false))
                        .collect(Collectors.toSet()));
    }
}
