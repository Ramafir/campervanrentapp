package pl.danielkolban.campervanrentapp.user;

import pl.danielkolban.campervanrentapp.rental.Rental;
import pl.danielkolban.campervanrentapp.vehicles.camper.Camper;

public class UserRentalMapper {
    
    static UserRentalDto toDto(Rental rental) {
        UserRentalDto dto = new UserRentalDto();
        dto.setId(rental.getId());
        dto.setCamperId(rental.getId());
        dto.setStart(rental.getStart());
        dto.setEnd(rental.getEnd());
        Camper camper = rental.getCamper();
        dto.setCamperId(camper.getId());
        dto.setCamperBrand(camper.getBrand());
        dto.setCamperModel(camper.getModel());
        dto.setCamperSerialNumber(camper.getSerialNumber());
        return dto;
    }
}
