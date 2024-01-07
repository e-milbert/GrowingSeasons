package plant.planner.plantplanner.repository;

import plant.planner.plantplanner.entity.PlantingDates;

import java.util.Optional;

public interface PlantingRepository extends BusinessKeyCheck<PlantingDates, Long> {

    Optional<PlantingDates> findByPlantId(long plantId);

}
