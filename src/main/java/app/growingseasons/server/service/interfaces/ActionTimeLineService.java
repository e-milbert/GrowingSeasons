package app.growingseasons.server.service.interfaces;

import app.growingseasons.server.dto.ActionTimeLineAggregate;
import app.growingseasons.server.dto.PlantDto;
import app.growingseasons.server.dto.TimeLinePlantDto;

import java.util.ArrayList;

public interface ActionTimeLineService {

    TimeLinePlantDto fuseTimelinesAndPlant(PlantDto plant);

    ArrayList<ActionTimeLineAggregate> getTimelinesOfPlant(long plantId);

    ActionTimeLineAggregate AddNewDates(ActionTimeLineAggregate atla);


    boolean DeleteDates(ActionTimeLineAggregate atla);

}
