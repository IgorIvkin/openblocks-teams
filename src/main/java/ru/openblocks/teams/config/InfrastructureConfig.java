package ru.openblocks.teams.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties("infrastructure")
public class InfrastructureConfig {

    public static final String GET_TECH_TOKEN_URL = "get-tech-token";

    @NotNull
    private KeycloakConfig keycloak;

    @Getter
    @Setter
    public static class KeycloakConfig {

        @NotBlank
        private String host;

        @NotBlank
        private String clientId;

        @NotBlank
        private String clientSecret;

        @NotNull
        private Map<String, String> urls;
    }
}
