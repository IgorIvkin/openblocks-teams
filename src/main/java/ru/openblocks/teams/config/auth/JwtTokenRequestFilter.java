package ru.openblocks.teams.config.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenRequestFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_COOKIE_NAME = "jwtToken";

    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("JwtTokenRequestFilter - start filtering, request: {}", request.getRequestURI());

        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

        final String token = getJwtToken(request);
        if (token != null) {
            if (!isJwtTokenExpired(token)) {
                mutableRequest.setHeader(HttpHeaders.AUTHORIZATION, BEARER + token);
            }
        }

        log.info("JwtTokenRequestFilter - finish filtering, request: {}", request.getRequestURI());

        // Продолжаем цепочку фильтрации
        filterChain.doFilter(mutableRequest, response);
    }

    private boolean isJwtTokenExpired(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
            return jwtClaimsSet.getExpirationTime().before(new Date());
        } catch (ParseException ex) {
            throw new IllegalStateException("Cannot parse JWT-token, reason: " + ex.getMessage());
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (JWT_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
