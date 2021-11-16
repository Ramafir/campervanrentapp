package pl.danielkolban.campervanrentapp.vehicles.camper;

import pl.danielkolban.campervanrentapp.rental.Rental;
import pl.danielkolban.campervanrentapp.user.User;

public class CamperRentalMapper {
    static CamperRentalDto toDto(Rental rental) {
        CamperRentalDto dto = new CamperRentalDto();
        dto.setId(rental.getId());
        dto.setStart(rental.getStart());
        dto.setEnd(rental.getEnd());

        User user = rental.getUser();
        dto.setUserId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPesel(user.getPesel());
        return dto;
    }
}
