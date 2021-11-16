package pl.danielkolban.campervanrentapp.rental;

import pl.danielkolban.campervanrentapp.user.User;
import pl.danielkolban.campervanrentapp.vehicles.camper.Camper;

public class RentalMapper {
    static RentalDto toDto(Rental rental) {
        RentalDto dto = new RentalDto();
        User user = rental.getUser();
        dto.setId(rental.getId());
        dto.setStart(rental.getStart());
        dto.setEnd(rental.getEnd());
        dto.setUserId(user.getId());
        Camper camper = rental.getCamper();
        dto.setCamperId(camper.getId());
        return dto;
    }
}
