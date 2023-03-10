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
@ConfigurationProperties("cs-roles")
public class CsRolesConfig {

    public static final String GET_ROLES_BY_USER_URL = "get-roles-by-user";

    public static final String ADD_ROLE_TO_USER_URL = "add-role-to-user";

    @NotBlank
    private String host;

    @NotNull
    private Map<String, String> urls;
}
