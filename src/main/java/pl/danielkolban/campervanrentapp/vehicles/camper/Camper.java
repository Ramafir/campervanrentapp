package pl.danielkolban.campervanrentapp.vehicles.camper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.danielkolban.campervanrentapp.rental.Rental;
import pl.danielkolban.campervanrentapp.vehicles.category.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Camper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String description;
    private String imgUrl;
    @Column(unique = true)
    private String serialNumber;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "camper")
    private List<Rental> rentals = new ArrayList<>();
}
