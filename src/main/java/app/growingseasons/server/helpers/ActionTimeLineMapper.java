package app.growingseasons.server.helpers;

import app.growingseasons.server.dto.ActionTimeLineAggregate;
import app.growingseasons.server.entity.FloweringDates;
import app.growingseasons.server.entity.HarvestingDates;
import app.growingseasons.server.entity.PlantingDates;
import app.growingseasons.server.entity.SowingDates;
import app.growingseasons.server.service.interfaces.PlantService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActionTimeLineMapper {


    ActionTimeLineMapper INSTANCE = Mappers.getMapper(ActionTimeLineMapper.class);


    @Mappings({

            @Mapping(source = "action.id", target = "actionId"),
            @Mapping(source = "action.timeLineAsJson", target = "jsonTimeline")
    })
    ActionTimeLineAggregate toActionTimeLineAggregate(long plantId, SowingDates action);

    @AfterMapping
    default void afterMapping(SowingDates action, @MappingTarget ActionTimeLineAggregate atla) {
        atla.setActionType(action.getClass().getSimpleName());

    }

    @Mappings({

            @Mapping(source = "action.id", target = "actionId"),
            @Mapping(source = "action.timeLineAsJson", target = "jsonTimeline")
    })
    ActionTimeLineAggregate toActionTimeLineAggregate(long plantId, PlantingDates action);

    @AfterMapping
    default void afterMapping(PlantingDates action, @MappingTarget ActionTimeLineAggregate atla) {
        atla.setActionType(action.getClass().getSimpleName());
    }

    @Mappings({

            @Mapping(source = "action.id", target = "actionId"),
            @Mapping(source = "action.timeLineAsJson", target = "jsonTimeline")
    })
    ActionTimeLineAggregate toActionTimeLineAggregate(long plantId, HarvestingDates action);

    @AfterMapping
    default void afterMapping(HarvestingDates action, @MappingTarget ActionTimeLineAggregate atla) {
        atla.setActionType(action.getClass().getSimpleName());
    }

    @Mappings({

            @Mapping(source = "action.id", target = "actionId"),
            @Mapping(source = "action.timeLineAsJson", target = "jsonTimeline")
    })
    ActionTimeLineAggregate toActionTimeLineAggregate(long plantId, FloweringDates action);

    @AfterMapping
    default void afterMapping(FloweringDates action, @MappingTarget ActionTimeLineAggregate atla) {
        atla.setActionType(action.getClass().getSimpleName());
    }

    SowingDates toSowingDates(ActionTimeLineAggregate atla, @Context PlantService plantS);

    @AfterMapping
    default void afterMapping(ActionTimeLineAggregate atla, @MappingTarget SowingDates sd, @Context PlantService plantS) {
        sd.setId(atla.getActionId());
        if (atla.getPlantId() != 0) {
            sd.setPlant(plantS.getPlant(atla.getPlantId()));
        }
        sd.setTimeLineAsJson(atla.getJsonTimeline());
    }

    HarvestingDates toHarvestingDates(ActionTimeLineAggregate atla, @Context PlantService plantS);

    @AfterMapping
    default void afterMapping(ActionTimeLineAggregate atla, @MappingTarget HarvestingDates hd,
                              @Context PlantService plantS) {
        hd.setId(atla.getActionId());
        if (atla.getPlantId() != 0) {
            hd.setPlant(plantS.getPlant(atla.getPlantId()));
        }
        hd.setTimeLineAsJson(atla.getJsonTimeline());
    }

    PlantingDates toPlantingDates(ActionTimeLineAggregate atla, @Context PlantService plantS);

    @AfterMapping
    default void afterMapping(ActionTimeLineAggregate atla, @MappingTarget PlantingDates pd,
                              @Context PlantService plantS) {
        pd.setId(atla.getActionId());
        if (atla.getPlantId() != 0) {
            pd.setPlant(plantS.getPlant(atla.getPlantId()));
        }
        pd.setTimeLineAsJson(atla.getJsonTimeline());
    }

    FloweringDates toFloweringDates(ActionTimeLineAggregate atla, @Context PlantService plantS);

    @AfterMapping
    default void afterMapping(ActionTimeLineAggregate atla, @MappingTarget FloweringDates fd,
                              @Context PlantService plantS) {
        fd.setId(atla.getActionId());
        if (atla.getPlantId() != 0) {
            fd.setPlant(plantS.getPlant(atla.getPlantId()));
        }
        fd.setTimeLineAsJson(atla.getJsonTimeline());
    }
}
