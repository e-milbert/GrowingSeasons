package app.growingseasons.server.repository;

import app.growingseasons.server.entity.FloweringDates;

import java.util.Optional;

public interface FloweringRepository extends BusinessKeyCheck<FloweringDates, Long> {

    Optional<FloweringDates> findByPlantId(long plantId);


}
