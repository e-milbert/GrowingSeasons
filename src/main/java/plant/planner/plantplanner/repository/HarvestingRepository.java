package plant.planner.plantplanner.repository;

import plant.planner.plantplanner.entity.HarvestingDates;

import java.util.Optional;

public interface HarvestingRepository extends BusinessKeyCheck<HarvestingDates, Long> {

    Optional<HarvestingDates> findByPlantId(long plantId);

}
