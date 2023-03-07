package ru.openblocks.teams.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.openblocks.teams.api.dto.error.ErrorMessage;
import ru.openblocks.teams.exception.CsRolesException;
import ru.openblocks.teams.exception.CsUsersException;
import ru.openblocks.teams.exception.InfraClientException;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return handleDefaultException(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage csUsersException(CsUsersException ex) {
        return handleDefaultException(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage csRolesException(CsRolesException ex) {
        return handleDefaultException(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage infraClientException(InfraClientException ex) {
        return handleDefaultException(ex);
    }

    private ErrorMessage handleDefaultException(Throwable ex) {
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .build();
    }
}

