package plant.planner.plantplanner.repository;

import plant.planner.plantplanner.entity.FloweringDates;

import java.util.Optional;

public interface FloweringRepository extends BusinessKeyCheck<FloweringDates, Long> {

    Optional<FloweringDates> findByPlantId(long plantId);


}
