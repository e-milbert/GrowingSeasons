package plant.planner.plantplanner.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plant.planner.plantplanner.dto.ExposureLevelDto;
import plant.planner.plantplanner.service.interfaces.ExposureLevelService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ExposureController {

    private final ExposureLevelService service;

    public ExposureController(ExposureLevelService service) {
        this.service = service;
    }

    @PostMapping("/exposure")
    public ResponseEntity<ExposureLevelDto> create(@RequestBody ExposureLevelDto newEntry) {
        if (newEntry.getId() == 0) {
            ExposureLevelDto result = service.addNewExposureLevel(newEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/exposures")
    public List<ExposureLevelDto> getAll() {
        return service.allExposureLevels();
    }

    @DeleteMapping("/exposure/{id}")
    public boolean delete(@PathVariable int id) {
        ExposureLevelDto dto = service.getExposureLevel(id);
        return service.deleteExposureLevel(dto);
    }


}
