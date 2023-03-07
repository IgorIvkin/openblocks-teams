package ru.openblocks.teams.client.dto.roles.getbyuser.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserRoleGetResponse {

    private String code;

    private String label;

    private String grantBy;

    private Instant createdAt;
}
