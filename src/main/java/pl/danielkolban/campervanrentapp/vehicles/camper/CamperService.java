package pl.danielkolban.campervanrentapp.vehicles.camper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CamperService {
    private CamperRepository camperRepository;
    private CamperMapper camperMapper;

    public CamperService(CamperRepository camperRepository, CamperMapper camperMapper) {
        this.camperRepository = camperRepository;
        this.camperMapper = camperMapper;
    }

    List<CamperDto> findAll() {
        return camperRepository.findAll()
                .stream()
                .map(camperMapper::toDto)
                .collect(Collectors.toList());
    }

    Optional<CamperDto> findById(Long id) {
        return camperRepository.findById(id).map(camperMapper::toDto);
    }

    List<CamperDto> findAllByNameOrSerialNumber(String text) {
        return camperRepository.findAllByNameOrSerialNumber(text)
                .stream()
                .map(camperMapper::toDto)
                .collect(Collectors.toList());
    }

    CamperDto save(CamperDto camper) {
        Optional<Camper> camperBySerialNo = camperRepository.findBySerialNumber(camper.getSerialNumber());
        camperBySerialNo.ifPresent(a -> {
            throw new DuplicateSerialNumberException();
        });
        return mapAndSave(camper);
    }

    CamperDto update(CamperDto camper) {
        Optional<Camper> camperBySerialNo = camperRepository.findBySerialNumber(camper.getSerialNumber());
        camperBySerialNo.ifPresent(a -> {
            if(!a.getId().equals(camper.getId()))
                throw new DuplicateSerialNumberException();
        });
        return mapAndSave(camper);
    }

    private CamperDto mapAndSave(CamperDto camper) {
        Camper camperEntity = camperMapper.toEntity(camper);
        Camper savedCamper = camperRepository.save(camperEntity);
        return camperMapper.toDto(savedCamper);
    }

    List<CamperRentalDto> getCamperRentals(Long id) {
        return camperRepository.findById(id)
                .map(Camper::getRentals)
                .orElseThrow(CamperNotFoundException::new)
                .stream()
                .map(CamperRentalMapper::toDto)
                .collect(Collectors.toList());
    }

}
