package pl.danielkolban.campervanrentapp.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.danielkolban.campervanrentapp.rental.Rental;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String pesel;
    @OneToMany(mappedBy = "user")
    private List<Rental> rentals = new ArrayList<>();
}
