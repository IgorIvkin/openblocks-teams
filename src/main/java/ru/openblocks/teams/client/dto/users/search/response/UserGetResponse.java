package ru.openblocks.teams.client.dto.users.search.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetResponse {

    private Long id;

    private String userName;

    private String lastName;

    private String firstName;

    private String patronymicName;

    private LocalDate birthDate;
}

