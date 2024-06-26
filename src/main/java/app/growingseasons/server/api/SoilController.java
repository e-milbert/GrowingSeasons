package app.growingseasons.server.api;

import app.growingseasons.server.dto.SoilDto;
import app.growingseasons.server.service.interfaces.SoilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SoilController {
    private final SoilService service;

    public SoilController(SoilService service) {
        this.service = service;
    }

    @PostMapping("/soil")
    public ResponseEntity<SoilDto> create(@RequestBody SoilDto newEntry) {
        try {
            SoilDto result = service.addNewSoil(newEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Logger.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/soils")
    public List<SoilDto> getAll() {
        return service.allSoils();
    }

    @DeleteMapping("/soil/{id}")
    public boolean delete(@PathVariable int id) {
        SoilDto dto = service.getSoil(id);
        return service.deleteSoil(dto);
    }


}

