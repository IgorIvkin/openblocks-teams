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
@ConfigurationProperties("cs-users")
public class CsUsersConfig {

    public static final String SEARCH_URL = "search";

    @NotBlank
    private String host;

    @NotNull
    private Map<String, String> urls;
}
