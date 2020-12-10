package pl.tomek.fixthat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Użytkownik z numerem telefonu już istnieje")
public class DuplicatePhoneNumberException extends RuntimeException {
}
