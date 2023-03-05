package ru.openblocks.teams.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.openblocks.teams.client.dto.users.search.response.UserGetResponse;
import ru.openblocks.teams.service.UserDataService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserDataService userDataService;

    @GetMapping("/search")
    public List<UserGetResponse> search(@RequestParam String searchText) {
        return userDataService.searchUsers(searchText);
    }
}
