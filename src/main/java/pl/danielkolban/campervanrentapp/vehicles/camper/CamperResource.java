package pl.danielkolban.campervanrentapp.vehicles.camper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/campers")
public class CamperResource {

    private CamperService camperService;

    public CamperResource(CamperService camperService) {
        this.camperService = camperService;
    }

    @GetMapping("")
    public List<CamperDto> findAll(@RequestParam(required = false) String text) {
        if(text != null)
            return camperService.findAllByNameOrSerialNumber(text);
        else
            return camperService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamperDto> findById(@PathVariable Long id) {
        return camperService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<CamperDto> save(@RequestBody CamperDto camper) {
        if(camper.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saved object cannot have id set");
        CamperDto savedCamper = camperService.save(camper);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCamper.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCamper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamperDto> update(@PathVariable Long id,
                                           @RequestBody CamperDto camper) {
        if(!id.equals(camper.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The updated object should have an id that matches the resource path id");
        CamperDto updatedCamper = camperService.update(camper);
        return ResponseEntity.ok(updatedCamper);
    }

    @GetMapping("/{id}/rentals")
    public List<CamperRentalDto> getCamperRentals(@PathVariable Long id) {
        return camperService.getCamperRentals(id);
    }
}
