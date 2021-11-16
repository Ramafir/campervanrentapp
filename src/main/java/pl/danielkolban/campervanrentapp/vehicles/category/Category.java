package pl.danielkolban.campervanrentapp.vehicles.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.danielkolban.campervanrentapp.vehicles.camper.Camper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private Set<Camper> campers = new HashSet<>();

    }

