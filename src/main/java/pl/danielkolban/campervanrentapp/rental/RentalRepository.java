package pl.danielkolban.campervanrentapp.rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> findByCamper_IdAndEndIsNull(Long camperId);
}
