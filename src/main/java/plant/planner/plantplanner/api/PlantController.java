package plant.planner.plantplanner.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.ActionTimeLineAggregate;
import plant.planner.plantplanner.dto.PlantDto;
import plant.planner.plantplanner.dto.PlantPlainDto;
import plant.planner.plantplanner.service.interfaces.ActionTimeLineService;
import plant.planner.plantplanner.service.interfaces.PlantService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PlantController {
    private final PlantService service;
    private final ActionTimeLineService timeLineService;

    public PlantController(PlantService service, ActionTimeLineService timeLineService) {
        this.service = service;
        this.timeLineService = timeLineService;
    }

    @PostMapping("/plant")
    public ResponseEntity<PlantDto> create(@RequestBody PlantDto newEntry) {
        if (newEntry.getId() == 0) {
            PlantDto result = service.addNewPlant(newEntry);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/plant/update")
    public ResponseEntity<?> update(@RequestBody PlantDto changed) {
        try {
            PlantDto result = service.updatePlant(changed);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            Logger.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/plant/{id}")
    public List<ActionTimeLineAggregate> addTimelines(@PathVariable long id, @RequestBody List<ActionTimeLineAggregate> timelines) {
        List<ActionTimeLineAggregate> addedTimelines = new ArrayList<>();
        if (!timelines.isEmpty()) {
            for (var timeline : timelines) {
                if (id == timeline.getPlantId()) {
                    timeLineService.AddNewDates(timeline);
                    addedTimelines.add(timeline);
                }
            }
        }
        return addedTimelines;
    }

    @GetMapping("/plainplants")
    public List<PlantPlainDto> getAllPlain() {
        return service.allPlantPlainDtos();
    }

    @GetMapping("/plant/{id}")
    public PlantDto getSinglePlant(@PathVariable long id) {
        return service.getPlantDto(id);
    }


    @GetMapping("/plants")
    public List<PlantDto> getAll() {
        return service.allPlantDtos();
    }

    @DeleteMapping("/plant/{id}")
    public boolean delete(@PathVariable long id) {
        PlantDto dto = service.getPlantDto(id);
        return service.deletePlant(dto);
    }


}
