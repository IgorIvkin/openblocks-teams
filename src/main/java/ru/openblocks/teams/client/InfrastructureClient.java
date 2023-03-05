package ru.openblocks.teams.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.openblocks.teams.client.dto.infra.token.TokenResponse;
import ru.openblocks.teams.config.InfrastructureConfig;
import ru.openblocks.teams.exception.InfraClientException;

/**
 * Этот компонент предназначен для взаимодействия с инфраструктурой предприятия, в частности,
 * для получения короткоживущих access-токенов технических учётных записей.
 */
@Slf4j
@Component
public class InfrastructureClient {

    private final RestTemplate restTemplate;

    private final InfrastructureConfig infrastructureConfig;

    @Autowired
    public InfrastructureClient(
            @Qualifier("infrastructure-rest-template") RestTemplate restTemplate,
            InfrastructureConfig infrastructureConfig) {
        this.restTemplate = restTemplate;
        this.infrastructureConfig = infrastructureConfig;
    }

    /**
     * Возвращает технический токен для доступа к ресурсам инфраструктуры.
     * Токен получается в Keycloak предприятия.
     *
     * @return технический токен
     */
    public TokenResponse getTechToken() {

        final String url = infrastructureConfig.getKeycloak().getHost()
                + infrastructureConfig.getKeycloak().getUrls().get(InfrastructureConfig.GET_TECH_TOKEN_URL);
        log.info("Get access token from Keycloak, url: {}", url);

        try {
            return restTemplate.postForObject(url, getAdminTokenRequest(), TokenResponse.class);
        } catch (Exception ex) {
            log.error("Cannot get access token from Keycloak", ex);
            throw new InfraClientException("Cannot get access token from Keycloak, reason: " + ex.getMessage());
        }
    }

    private HttpEntity<MultiValueMap<String, String>> getAdminTokenRequest() {
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("client_id", infrastructureConfig.getKeycloak().getClientId());
        requestData.add("client_secret", infrastructureConfig.getKeycloak().getClientSecret());
        requestData.add("grant_type", "client_credentials");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(requestData, headers);
    }
}
