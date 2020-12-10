package pl.tomek.fixthat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Użytkownik z taką nazwią użytkownika już istnieje")
public class DuplicateUsernameException extends RuntimeException {
}
