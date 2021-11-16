package pl.danielkolban.campervanrentapp.vehicles.camper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CamperRepository extends JpaRepository<Camper, Long> {
    @Query("select a from Camper a where lower(a.brand) like lower(concat('%', :search, '%')) " +
            "or lower(a.model) like lower(concat('%', :search, '%')) " +
            "or lower(a.serialNumber) like lower(concat('%', :search, '%'))")
    List<Camper> findAllByNameOrSerialNumber(@Param("search") String search);


    Optional<Camper> findBySerialNumber(String serialNumber);
}
