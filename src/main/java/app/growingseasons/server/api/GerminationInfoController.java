package app.growingseasons.server.api;

import app.growingseasons.server.dto.GerminationTempDto;
import app.growingseasons.server.service.interfaces.GerminationTempService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class GerminationInfoController {

    private final GerminationTempService service;

    public GerminationInfoController(GerminationTempService service) {
        this.service = service;
    }

    @PostMapping("/germination")
    public ResponseEntity<GerminationTempDto> create(@RequestBody GerminationTempDto newEntry) {
        if (newEntry.getId() == 0) {
            GerminationTempDto result = service.addNewGerminationTemp(newEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/germination/update")
    public ResponseEntity<GerminationTempDto> update(@RequestBody GerminationTempDto editedEntry) {

        if (service.getGerminationTemp(editedEntry.getId()) != null) {
            GerminationTempDto result = service.updateGerminationTemp(editedEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/germinations")
    public List<GerminationTempDto> getAll() {
        return service.allGerminationTemps();
    }

    @DeleteMapping("/germination/{id}")
    public boolean delete(@PathVariable long id) {
        GerminationTempDto dto = service.getGerminationTemp(id);
        return service.deleteGerminationTemp(dto);
    }


}
