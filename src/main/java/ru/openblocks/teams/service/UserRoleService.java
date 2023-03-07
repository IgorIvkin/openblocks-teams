package ru.openblocks.teams.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.openblocks.teams.client.CsRolesClient;
import ru.openblocks.teams.client.dto.roles.getbyuser.response.UserRoleGetResponse;

import java.util.List;

/**
 * Этот сервис предназначен для взаимодействия с ролями пользователей из общего сервиса "Роли".
 */
@Slf4j
@Service
public class UserRoleService {

    private final CsRolesClient csRolesClient;

    @Autowired
    public UserRoleService(CsRolesClient csRolesClient) {
        this.csRolesClient = csRolesClient;
    }

    /**
     * Получает список ролей пользователя по его логину.
     *
     * @param userName логин пользователя
     * @return список ролей пользователя по логину
     */
    public List<UserRoleGetResponse> getUserRolesByUserName(String userName) {
        return csRolesClient.getUserRolesByUserName(userName);
    }
}
