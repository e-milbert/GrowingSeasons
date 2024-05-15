package app.growingseasons.server.helpers;

import app.growingseasons.server.dto.ActionTimeLineAggregate;
import app.growingseasons.server.entity.FloweringDates;
import app.growingseasons.server.entity.HarvestingDates;
import app.growingseasons.server.entity.PlantingDates;
import app.growingseasons.server.entity.SowingDates;
import app.growingseasons.server.service.interfaces.PlantService;
import org.springframework.stereotype.Component;
import org.tinylog.Logger;

@Component
public class MapperDynHelper {

    private final ActionTimeLineMapper mapper;
    private final PlantService plantS;


    public MapperDynHelper(ActionTimeLineMapper mapper, PlantService plantS) {
        this.mapper = mapper;
        this.plantS = plantS;
    }

    public Object fromActionTimeLine(ActionTimeLineAggregate atla) throws IllegalArgumentException {
        String actionType = atla.getActionType().toLowerCase();
        if (actionType.contains("sow")) {
            return mapper.toSowingDates(atla, plantS);

        } else if (actionType.contains("harvest")) {
            return mapper.toHarvestingDates(atla, plantS);

        } else if (actionType.contains("plant")) {
            return mapper.toPlantingDates(atla, plantS);

        } else if (actionType.contains("flower")) {
            return mapper.toFloweringDates(atla, plantS);

        } else {
            Logger.info(String.format("provided argument %s", atla));
            throw new IllegalArgumentException(String.format("invalid argument %s", atla));
        }
    }

    public ActionTimeLineAggregate fromAnyEntityDates(Object entityDates) throws IllegalArgumentException {

        if (entityDates.getClass() == SowingDates.class) {
            return mapper.toActionTimeLineAggregate(((SowingDates) entityDates).getPlant().getId(), (SowingDates) entityDates);
        } else if (entityDates.getClass() == HarvestingDates.class) {
            return mapper.toActionTimeLineAggregate(((HarvestingDates) entityDates).getPlant().getId(), (HarvestingDates) entityDates);

        } else if (entityDates.getClass() == PlantingDates.class) {
            return mapper.toActionTimeLineAggregate(((PlantingDates) entityDates).getPlant().getId(), (PlantingDates) entityDates);

        } else if (entityDates.getClass() == FloweringDates.class) {
            return mapper.toActionTimeLineAggregate(((FloweringDates) entityDates).getPlant().getId(), (FloweringDates) entityDates);
        } else {
            Logger.info(String.format("provided argument %s", entityDates));
            throw new IllegalArgumentException();
        }
    }

}
