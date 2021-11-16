package pl.danielkolban.campervanrentapp.vehicles.camper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brak wyposażenia o takim ID")
public class CamperNotFoundException extends RuntimeException {
}
