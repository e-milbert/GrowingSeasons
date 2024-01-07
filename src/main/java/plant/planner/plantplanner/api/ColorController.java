package plant.planner.plantplanner.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plant.planner.plantplanner.dto.ColorDto;
import plant.planner.plantplanner.service.interfaces.ColorsService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ColorController {

    private final ColorsService service;

    public ColorController(ColorsService service) {
        this.service = service;
    }

    @PostMapping("/color")
    public ResponseEntity<ColorDto> create(@RequestBody ColorDto newColor) {

        if (newColor.getId() == 0) {
            ColorDto result = service.addNewColor(newColor);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/colors")
    public List<ColorDto> getAll() {
        return service.allColors();
    }

    @DeleteMapping("/color/{id}")
    public boolean delete(@PathVariable int id) {

        ColorDto dto = service.getColor(id);

        return service.deleteColor(dto);

    }
}