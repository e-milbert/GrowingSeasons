package plant.planner.plantplanner.repository;

import plant.planner.plantplanner.entity.Plant;

public interface PlantRepository extends BusinessKeyCheck<Plant, Long> {
    boolean existsById(long id);
}
