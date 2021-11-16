package pl.danielkolban.campervanrentapp.rental;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "To przypisanie już zostało zakończone")
public class RentalAlreadyFinishedException extends RuntimeException {
}
