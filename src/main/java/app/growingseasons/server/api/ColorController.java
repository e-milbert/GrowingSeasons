package app.growingseasons.server.api;

import app.growingseasons.server.dto.ColorDto;
import app.growingseasons.server.service.interfaces.ColorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

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

        try {
            ColorDto result = service.addNewColor(newColor);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Logger.error(e);
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