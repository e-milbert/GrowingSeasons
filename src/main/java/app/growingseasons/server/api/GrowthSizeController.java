package app.growingseasons.server.api;

import app.growingseasons.server.dto.SizeDto;
import app.growingseasons.server.service.interfaces.GrowthSizeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GrowthSizeController {

    private final GrowthSizeService service;

    public GrowthSizeController(GrowthSizeService service) {
        this.service = service;
    }

    @PostMapping("/growth")
    public ResponseEntity<SizeDto> create(@RequestBody SizeDto newEntry) {
        try {
            SizeDto result = service.addNewGrowthSize(newEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Logger.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/growths")
    public List<SizeDto> getAll() {
        return service.allGrowthSizes();
    }

    @DeleteMapping("/growth/{id}")
    public boolean delete(@PathVariable int id) {
        SizeDto dto = service.getGrowthSize(id);
        return service.deleteGrowthSize(dto);
    }


}
