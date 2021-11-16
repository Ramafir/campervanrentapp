package pl.danielkolban.campervanrentapp.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rentals")
public class RentalResource {
    
    private RentalService rentalService;

    public RentalResource(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("")
    public ResponseEntity<RentalDto> saveRental(@RequestBody RentalDto rental) {
        RentalDto savedRental;
        try {
            savedRental = rentalService.createRental(rental);
        } catch(InvalidRentalException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRental.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedRental);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity finishRental(@PathVariable Long id) {
        LocalDateTime endDate = rentalService.finishRental(id);
        return ResponseEntity.accepted().body(endDate);
    }
}
