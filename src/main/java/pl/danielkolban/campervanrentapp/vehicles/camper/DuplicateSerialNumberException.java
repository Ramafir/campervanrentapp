package pl.danielkolban.campervanrentapp.vehicles.camper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "Wyposażenie z takim numerem seryjnym już istnieje")
public class DuplicateSerialNumberException extends RuntimeException {
}
