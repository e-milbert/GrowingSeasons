package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.ActionTimeLineAggregate;
import plant.planner.plantplanner.dto.PlantDto;
import plant.planner.plantplanner.dto.TimeLinePlantDto;

import java.util.ArrayList;

public interface ActionTimeLineService {

    TimeLinePlantDto fuseTimelinesAndPlant(PlantDto plant);

    ArrayList<ActionTimeLineAggregate> getTimelinesOfPlant(long plantId);

    ActionTimeLineAggregate AddNewDates(ActionTimeLineAggregate atla);


    boolean DeleteDates(ActionTimeLineAggregate atla);

}
