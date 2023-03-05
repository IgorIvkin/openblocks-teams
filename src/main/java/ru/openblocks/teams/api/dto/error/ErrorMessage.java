package ru.openblocks.teams.api.dto.error;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String message;
}

