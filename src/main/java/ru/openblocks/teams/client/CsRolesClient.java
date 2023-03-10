package ru.openblocks.teams.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.openblocks.teams.client.dto.roles.create.request.UserRoleCreateRequest;
import ru.openblocks.teams.client.dto.roles.getbyuser.response.UserRoleGetResponse;
import ru.openblocks.teams.config.CsRolesConfig;
import ru.openblocks.teams.exception.CsRolesException;
import ru.openblocks.teams.service.InfrastructureService;

import java.util.List;

/**
 * Этот клиент предназначен для взаимодействия с общим сервисом "Роли".
 */
@Slf4j
@Component
public class CsRolesClient extends AbstractCsClient {

    private final RestTemplate restTemplate;

    private final InfrastructureService infrastructureService;

    private final CsRolesConfig csRolesConfig;

    @Autowired
    public CsRolesClient(
            @Qualifier("cs-roles-rest-template") RestTemplate restTemplate,
            CsRolesConfig csRolesConfig,
            InfrastructureService infrastructureService) {
        this.restTemplate = restTemplate;
        this.csRolesConfig = csRolesConfig;
        this.infrastructureService = infrastructureService;
    }

    /**
     * Получает список ролей пользователя по его логину. Обращение происходит к общему сервису "Роли".
     *
     * @param userName логин пользователя
     * @return список ролей пользователя
     */
    @Retryable(maxAttemptsExpression = "${cs-roles.max-retries}",
            backoff = @Backoff(delayExpression = "${cs-roles.retry-delay-ms}"))
    public List<UserRoleGetResponse> getUserRolesByUserName(String userName) {

        final String token = infrastructureService.getTechToken();
        final String url = buildSearchByUserUrl(userName);
        log.info("Request to Common Service Roles, get user roles, url: {}", url);

        try {

            ParameterizedTypeReference<List<UserRoleGetResponse>> typeReference
                    = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<List<UserRoleGetResponse>> usersResponse =
                    restTemplate.exchange(url, HttpMethod.GET, getBasicHttpRequest(token), typeReference);
            return usersResponse.getBody();

        } catch (Exception ex) {
            log.error("Cannot get user roles in Common Service Roles", ex);
            throw new CsRolesException("Cannot get user roles in Common Service Roles, reason: " + ex.getMessage());
        }
    }

    /**
     * Добавляет роль пользователю.
     *
     * @param request запрос на добавление роли пользователю
     */
    @Retryable(maxAttemptsExpression = "${cs-roles.max-retries}",
            backoff = @Backoff(delayExpression = "${cs-roles.retry-delay-ms}"))
    public void addRoleToUser(UserRoleCreateRequest request) {

        final String token = infrastructureService.getTechToken();
        final String url = csRolesConfig.getHost()
                + csRolesConfig.getUrls().get(CsRolesConfig.ADD_ROLE_TO_USER_URL);
        log.info("Request to Common Service Roles, add role to user, url: {}, request: {}", url, request);

        try {

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    getBasicTypedHttpRequest(token, request),
                    UserRoleCreateRequest.class
            );

        } catch (Exception ex) {
            log.error("Cannot add role to user in Common Service Roles", ex);
            throw new CsRolesException("Cannot add role to user in Common Service Roles, reason: " + ex.getMessage());
        }
    }

    private String buildSearchByUserUrl(String userName) {
        String url = csRolesConfig.getHost()
                + csRolesConfig.getUrls().get(CsRolesConfig.GET_ROLES_BY_USER_URL);
        return url.replace("{userName}", userName);
    }
}
