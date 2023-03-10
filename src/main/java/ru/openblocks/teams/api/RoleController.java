package ru.openblocks.teams.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.openblocks.teams.client.dto.roles.create.request.UserRoleCreateRequest;
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/users")
    public void addRoleToUser(@Valid @RequestBody UserRoleCreateRequest request) {
        userRoleService.addRoleToUser(request);
    }
}
