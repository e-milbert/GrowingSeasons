package plant.planner.plantplanner.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plant.planner.plantplanner.dto.SizeDto;
import plant.planner.plantplanner.service.interfaces.GrowthSizeService;

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
        if (newEntry.getId() == 0) {
            SizeDto result = service.addNewGrowthSize(newEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
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
