package app.growingseasons.server.api;

import app.growingseasons.server.dto.ActionTimeLineAggregate;
import app.growingseasons.server.dto.TimeLinePlantDto;
import app.growingseasons.server.service.interfaces.ActionTimeLineService;
import app.growingseasons.server.service.interfaces.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ActionTimeLineController {

    private final ActionTimeLineService service;
    private final PlantService plantService;

    public ActionTimeLineController(ActionTimeLineService service, PlantService plantService) {
        this.service = service;
        this.plantService = plantService;
    }


    @PutMapping("/timeline")
    public ResponseEntity<ActionTimeLineAggregate> update(@RequestBody ActionTimeLineAggregate changed) {
        if (plantService.plantExists(changed.getPlantId())) {
            var result = service.AddNewDates(changed);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/actiontimes")
    public List<TimeLinePlantDto> getTimelinesByPlant() {
        List<TimeLinePlantDto> timelines = new ArrayList<>();

        for (var plant : plantService.allPlantDtos()) {
            timelines.add(service.fuseTimelinesAndPlant(plant));
        }
        return timelines;
    }

    @GetMapping("/actiontimes/{plantid}")
    public TimeLinePlantDto getTimelinesOfSinglePlant(@PathVariable long plantid) {

        return service.fuseTimelinesAndPlant(plantService.getPlantDto(plantid));
    }

}
