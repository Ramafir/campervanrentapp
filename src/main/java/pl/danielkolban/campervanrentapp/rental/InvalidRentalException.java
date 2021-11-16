package pl.danielkolban.campervanrentapp.rental;

public class InvalidRentalException extends RuntimeException {
    public InvalidRentalException(String message) {
        super(message);
    }
}
