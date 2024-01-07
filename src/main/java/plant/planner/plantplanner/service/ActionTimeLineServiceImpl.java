package plant.planner.plantplanner.service;

import org.springframework.stereotype.Service;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.ActionTimeLineAggregate;
import plant.planner.plantplanner.dto.PlantDto;
import plant.planner.plantplanner.dto.TimeLinePlantDto;
import plant.planner.plantplanner.entity.FloweringDates;
import plant.planner.plantplanner.entity.HarvestingDates;
import plant.planner.plantplanner.entity.PlantingDates;
import plant.planner.plantplanner.entity.SowingDates;
import plant.planner.plantplanner.helpers.ActionTimeLineMapper;
import plant.planner.plantplanner.helpers.MapperDynHelper;
import plant.planner.plantplanner.repository.FloweringRepository;
import plant.planner.plantplanner.repository.HarvestingRepository;
import plant.planner.plantplanner.repository.PlantingRepository;
import plant.planner.plantplanner.repository.SowingRepository;
import plant.planner.plantplanner.service.interfaces.ActionTimeLineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActionTimeLineServiceImpl implements ActionTimeLineService {

    private final SowingRepository sowingR;
    private final PlantingRepository plantingR;
    private final FloweringRepository floweringR;
    private final HarvestingRepository harvestingR;
    private final ActionTimeLineMapper mapper;
    private final MapperDynHelper mapHelp;


    public ActionTimeLineServiceImpl(SowingRepository sowingR, PlantingRepository plantingR, FloweringRepository floweringR, HarvestingRepository harvestingR, ActionTimeLineMapper mapper, MapperDynHelper mapHelp) {
        this.sowingR = sowingR;
        this.plantingR = plantingR;
        this.floweringR = floweringR;
        this.harvestingR = harvestingR;
        this.mapper = mapper;
        this.mapHelp = mapHelp;
    }

    private List<Integer> timelineToIntegerList(String timeline) {
        List<Integer> tl = new ArrayList<>();
        String[] numbers = timeline.replaceAll("[\\[\\]]", "").split(",");
        for (String number : numbers) {
            if (!number.trim().isEmpty()) {
                tl.add(Integer.parseInt(number.trim()));
            }
        }
        return tl;
    }

    public TimeLinePlantDto fuseTimelinesAndPlant(PlantDto plant) {

        TimeLinePlantDto timeLinePlantDto = new TimeLinePlantDto();

        ArrayList<ActionTimeLineAggregate> tls = getTimelinesOfPlant(plant.getId());

        timeLinePlantDto.setPlantId(plant.getId());

        timeLinePlantDto.setName(plant.getCommonName());

        for (var timeline : tls) {
            var name = timeline.getActionType();


            if (name.contains("sow")) {
                timeLinePlantDto.setSowingId(timeline.getActionId());

                timeLinePlantDto.setSowing(timelineToIntegerList(timeline.getJsonTimeline()));


            } else if (name.contains("plant")) {
                timeLinePlantDto.setPlantingId(timeline.getActionId());


                timeLinePlantDto.setPlanting(timelineToIntegerList(timeline.getJsonTimeline()));


            } else if (name.contains("flower") || name.contains("bloom")) {
                timeLinePlantDto.setBloomingId(timeline.getActionId());

                timeLinePlantDto.setBloom(timelineToIntegerList(timeline.getJsonTimeline()));


            } else if (name.contains("harvest")) {
                timeLinePlantDto.setHarvestId(timeline.getActionId());

                timeLinePlantDto.setHarvest(timelineToIntegerList(timeline.getJsonTimeline()));

            }

        }

        return timeLinePlantDto;

    }

    public ArrayList<ActionTimeLineAggregate> getTimelinesOfPlant(long plantId) {
        ArrayList<ActionTimeLineAggregate> timelines = new ArrayList<>();

        Optional<SowingDates> s = sowingR.findByPlantId(plantId);
        SowingDates sd = s.orElse(null);
        if (sd != null) {
            timelines.add(mapper.toActionTimeLineAggregate(plantId, sd));
        }

        Optional<PlantingDates> p = plantingR.findByPlantId(plantId);
        PlantingDates pd = p.orElse(null);
        if (pd != null) {
            timelines.add(mapper.toActionTimeLineAggregate(plantId, pd));
        }

        Optional<HarvestingDates> h = harvestingR.findByPlantId(plantId);
        HarvestingDates hd = h.orElse(null);
        if (hd != null) {
            timelines.add(mapper.toActionTimeLineAggregate(plantId, hd));
        }

        Optional<FloweringDates> f = floweringR.findByPlantId(plantId);
        FloweringDates fd = f.orElse(null);
        if (fd != null) {
            timelines.add(mapper.toActionTimeLineAggregate(plantId, fd));
        }


        return timelines;
    }

    public ActionTimeLineAggregate AddNewDates(ActionTimeLineAggregate atla) {
        try {
            var newDate = mapHelp.fromActionTimeLine(atla);
            String actionType = atla.getActionType().toLowerCase();

            if (actionType.contains("sow")) {
                SowingDates sd = sowingR.save((SowingDates) newDate);
                return mapHelp.fromAnyEntityDates(sd);

            } else if (actionType.contains("harvest")) {
                HarvestingDates hd = harvestingR.save((HarvestingDates) newDate);
                return mapHelp.fromAnyEntityDates(hd);

            } else if (actionType.contains("plant")) {
                PlantingDates pd = plantingR.save((PlantingDates) newDate);
                return mapHelp.fromAnyEntityDates(pd);

            } else if (actionType.contains("flower")) {
                FloweringDates fd = floweringR.save((FloweringDates) newDate);
                return mapHelp.fromAnyEntityDates(fd);

            } else {
                Logger.info(String.format("provided argument %s", atla));
                throw new IllegalArgumentException(String.format("invalid argument %s", atla));
            }
        } catch (IllegalArgumentException ex) {
            Logger.error(ex);
            throw ex;
        }

    }


    public boolean DeleteDates(ActionTimeLineAggregate atla) {

        String actionType = atla.getActionType();
        var oldDate = mapHelp.fromActionTimeLine(atla);
        try {
            if (actionType.contains("sow")) {
                sowingR.delete((SowingDates) oldDate);

            } else if (actionType.contains("harvest")) {
                harvestingR.delete((HarvestingDates) oldDate);

            } else if (actionType.contains("plant")) {
                plantingR.delete((PlantingDates) oldDate);

            } else if (actionType.contains("flower") || actionType.contains("bloom")) {
                floweringR.delete((FloweringDates) oldDate);

            } else {
                Logger.info(String.format("provided argument %s", atla));
                throw new IllegalArgumentException(String.format("invalid argument %s", atla));
            }

            return true;

        } catch (Exception exception) {
            Logger.error(exception);
            return false;
        }
    }


    public ActionTimeLineAggregate updateDates(ActionTimeLineAggregate changed) throws IllegalArgumentException {
        try {
            var newDates = mapHelp.fromActionTimeLine(changed);

            if (newDates.getClass() == SowingDates.class) {

                Optional<SowingDates> opt = sowingR.findByPlantId(changed.getPlantId());

                if (opt.isPresent()) {
                    var res = opt.orElse(null);
                    ((SowingDates) newDates).setId(res.getId());
                    var dates = sowingR.save((SowingDates) newDates);
                    return mapHelp.fromAnyEntityDates(dates);
                }

            } else if ((newDates.getClass() == HarvestingDates.class)) {

                Optional<HarvestingDates> opt = harvestingR.findByPlantId(changed.getPlantId());

                if (opt.isPresent()) {
                    var res = opt.orElse(null);
                    ((HarvestingDates) newDates).setId(res.getId());
                    var dates = harvestingR.save((HarvestingDates) newDates);
                    return mapHelp.fromAnyEntityDates(dates);
                }

            } else if ((newDates.getClass() == PlantingDates.class)) {

                Optional<PlantingDates> opt = plantingR.findByPlantId(changed.getPlantId());

                if (opt.isPresent()) {
                    var res = opt.orElse(null);
                    ((PlantingDates) newDates).setId(res.getId());
                    var dates = plantingR.save((PlantingDates) newDates);
                    return mapHelp.fromAnyEntityDates(dates);
                }

            } else if ((newDates.getClass() == FloweringDates.class)) {

                Optional<FloweringDates> opt = floweringR.findByPlantId(changed.getPlantId());

                if (opt.isPresent()) {
                    var res = opt.orElse(null);
                    ((FloweringDates) newDates).setId(res.getId());
                    var dates = floweringR.save((FloweringDates) newDates);
                    return mapHelp.fromAnyEntityDates(dates);
                }

            } else {

                Logger.info(String.format("provided argument %s", changed));
                throw new IllegalArgumentException(String.format("invalid argument %s", changed));
            }
        } catch (IllegalArgumentException ex) {
            Logger.error(ex);
            throw ex;
        }

        return null;

    }
}

