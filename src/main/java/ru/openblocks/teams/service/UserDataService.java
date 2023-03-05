package ru.openblocks.teams.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.openblocks.teams.client.CsUsersClient;
import ru.openblocks.teams.client.dto.users.search.response.UserGetResponse;

import java.util.List;

/**
 * Этот сервис предназначен для взаимодействия с пользователями из общего сервиса "Пользователи".
 */
@Slf4j
@Service
public class UserDataService {

    private final CsUsersClient csUsersClient;

    @Autowired
    public UserDataService(CsUsersClient csUsersClient) {
        this.csUsersClient = csUsersClient;
    }

    /**
     * Возвращает пользователей по поисковому запросу. Запрос идёт в общий сервис "Пользователи".
     *
     * @param searchText поисковый запрос
     * @return список пользователей по запросу
     */
    public List<UserGetResponse> searchUsers(String searchText) {
        return csUsersClient.search(searchText);
    }
}
