package ru.openblocks.teams.client.dto.roles.create.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRoleCreateRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String roleCode;
}
