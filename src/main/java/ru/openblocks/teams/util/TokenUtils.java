package ru.openblocks.teams.util;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.util.Date;

/**
 * Вспомогательные функции для работы с JWT-токеном.
 */
@UtilityClass
public class TokenUtils {

    /**
     * Проверяет, что JWT-токен не истёк.
     *
     * @param token JWT-токен
     * @return true, если JWT-токен истёк
     */
    public static boolean isJwtTokenExpired(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
            return jwtClaimsSet.getExpirationTime().before(new Date());
        } catch (ParseException ex) {
            throw new IllegalStateException("Cannot parse JWT-token, reason: " + ex.getMessage());
        }
    }
}
