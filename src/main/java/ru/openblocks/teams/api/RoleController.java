package ru.openblocks.teams.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.openblocks.teams.client.dto.roles.getbyuser.response.UserRoleGetResponse;
import ru.openblocks.teams.service.UserRoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final UserRoleService userRoleService;

    @GetMapping("/users/{userName}")
    public List<UserRoleGetResponse> getUserRolesByUserName(@PathVariable String userName) {
        return userRoleService.getUserRolesByUserName(userName);
    }
}
