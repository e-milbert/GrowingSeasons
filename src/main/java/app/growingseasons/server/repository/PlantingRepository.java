package app.growingseasons.server.repository;

import app.growingseasons.server.entity.PlantingDates;

import java.util.Optional;

public interface PlantingRepository extends BusinessKeyCheck<PlantingDates, Long> {

    Optional<PlantingDates> findByPlantId(long plantId);

}
