package ru.openblocks.teams.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.openblocks.teams.client.dto.users.search.response.UserGetResponse;
import ru.openblocks.teams.config.CsUsersConfig;
import ru.openblocks.teams.exception.CsUsersException;
import ru.openblocks.teams.service.InfrastructureService;

import java.util.List;

/**
 * Этот клиент предназначен для взаимодействия с общим сервисом "Пользователи".
 */
@Slf4j
@Component
public class CsUsersClient {

    private static final String BEARER = "Bearer ";

    private final RestTemplate restTemplate;

    private final InfrastructureService infrastructureService;

    private final CsUsersConfig csUsersConfig;

    @Autowired
    public CsUsersClient(
            @Qualifier("cs-users-rest-template") RestTemplate restTemplate,
            CsUsersConfig csUsersConfig,
            InfrastructureService infrastructureService) {
        this.restTemplate = restTemplate;
        this.csUsersConfig = csUsersConfig;
        this.infrastructureService = infrastructureService;
    }

    /**
     * Возвращает список пользователей по поисковому запросу от общего сервиса пользователей.
     *
     * @param searchText поисковый запрос
     * @return список пользователей
     */
    @Retryable(maxAttemptsExpression = "${cs-users.max-retries}",
            backoff = @Backoff(delayExpression = "${cs-users.retry-delay-ms}"))
    public List<UserGetResponse> search(String searchText) {

        final String token = infrastructureService.getTechToken();
        final String url = csUsersConfig.getHost()
                + csUsersConfig.getUrls().get(CsUsersConfig.SEARCH_URL)
                + "?searchText={arg1}";
        log.info("Request to Common Service Users, url: {}, request: {}", url, searchText);

        try {

            ParameterizedTypeReference<List<UserGetResponse>> typeReference
                    = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<List<UserGetResponse>> usersResponse =
                    restTemplate.exchange(url, HttpMethod.GET, getBasicHttpRequest(token), typeReference, searchText);
            return usersResponse.getBody();

        } catch (Exception ex) {
            log.error("Cannot search users in Common Service Users", ex);
            throw new CsUsersException("Cannot search users in Common Service Users, reason: " + ex.getMessage());
        }
    }

    private <T> HttpEntity<T> getBasicTypedHttpRequest(String token,
                                                       T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + token);
        return new HttpEntity<>(request, headers);
    }

    private HttpEntity<Void> getBasicHttpRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + token);
        return new HttpEntity<>(headers);
    }

}
