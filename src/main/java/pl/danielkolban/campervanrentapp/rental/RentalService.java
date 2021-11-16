package pl.danielkolban.campervanrentapp.rental;

import org.springframework.stereotype.Service;
import pl.danielkolban.campervanrentapp.user.User;
import pl.danielkolban.campervanrentapp.user.UserRepository;
import pl.danielkolban.campervanrentapp.vehicles.camper.Camper;
import pl.danielkolban.campervanrentapp.vehicles.camper.CamperRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentalService {
    private RentalRepository rentalRepository;
    private CamperRepository camperRepository;
    private UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, CamperRepository camperRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.camperRepository = camperRepository;
        this.userRepository = userRepository;
    }

    RentalDto createRental(RentalDto rentalDto) {
        Optional<Rental> activeRentalForCamper = rentalRepository
                .findByCamper_IdAndEndIsNull(rentalDto.getCamperId());
        activeRentalForCamper.ifPresent((a) -> {
            throw new InvalidRentalException("This vehicle is currently assigned to someone");
        });
        Optional<User> user = userRepository.findById(rentalDto.getUserId());
        Optional<Camper> camper = camperRepository.findById(rentalDto.getCamperId());
        Rental rental = new Rental();
        Long userId = rentalDto.getUserId();
        Long camperId = rentalDto.getCamperId();
        rental.setUser(user.orElseThrow(() ->
                new InvalidRentalException("No user with id: " + userId)));
        rental.setCamper(camper.orElseThrow(() ->
                new InvalidRentalException("No vehicle with id: " + camperId)));
        rental.setStart(LocalDateTime.now());
        return RentalMapper.toDto(rentalRepository.save(rental));
    }

    @Transactional
    public LocalDateTime finishRental(Long rentalId) {
        Optional<Rental> rental = rentalRepository.findById(rentalId);
        Rental rentalEntity = rental.orElseThrow(RentalNotFoundException::new);
        if(rentalEntity.getEnd() != null)
            throw new RentalAlreadyFinishedException();
        else
            rentalEntity.setEnd(LocalDateTime.now());
        return rentalEntity.getEnd();
    }
}
