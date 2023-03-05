package ru.openblocks.teams.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.openblocks.teams.client.InfrastructureClient;
import ru.openblocks.teams.client.dto.infra.token.TokenResponse;
import ru.openblocks.teams.util.TokenUtils;

import java.util.Objects;

/**
 * Этот сервис служит для взаимодействия с инфраструктурными компонентами предприятия.
 */
@Slf4j
@Service
public class InfrastructureService {

    private final InfrastructureClient infrastructureClient;

    @Autowired
    public InfrastructureService(InfrastructureClient infrastructureClient) {
        this.infrastructureClient = infrastructureClient;
    }

    /**
     * Кэш для технического токена.
     */
    private volatile String techToken = null;

    /**
     * Возвращает технический токен для запросов к сервисам в составе инфраструктуры предприятия.
     * Токен кешируется на время его существования.
     *
     * @return технический токен
     */
    public String getTechToken() {
        if (isTechTokenExpired()) {
            final TokenResponse response = infrastructureClient.getTechToken();
            techToken = response.getAccessToken();
        }
        return techToken;
    }

    private boolean isTechTokenExpired() {
        return Objects.isNull(techToken) ||
                TokenUtils.isJwtTokenExpired(techToken);
    }
}
