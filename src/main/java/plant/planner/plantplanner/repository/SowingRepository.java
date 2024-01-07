package plant.planner.plantplanner.repository;

import plant.planner.plantplanner.entity.SowingDates;

import java.util.Optional;

public interface SowingRepository extends BusinessKeyCheck<SowingDates, Long> {

    Optional<SowingDates> findByPlantId(long plantId);

}
