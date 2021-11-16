package pl.danielkolban.campervanrentapp.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserRentalDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long camperId;
    private String camperBrand;
    private String camperModel;
    private String camperSerialNumber;
}
