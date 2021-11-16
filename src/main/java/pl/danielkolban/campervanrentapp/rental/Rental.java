package pl.danielkolban.campervanrentapp.rental;

import lombok.Getter;
import lombok.Setter;
import pl.danielkolban.campervanrentapp.vehicles.camper.Camper;
import pl.danielkolban.campervanrentapp.user.User;

import javax.persistence.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "camper_id")
    private Camper camper;
}
