package pl.danielkolban.campervanrentapp.vehicles.camper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CamperDto {
    private Long id;
    private String brand;
    private String model;
    private String description;
    private String serialNumber;
    private String imgUrl;
    private String category;
}
