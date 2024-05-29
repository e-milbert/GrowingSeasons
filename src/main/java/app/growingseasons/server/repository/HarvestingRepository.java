package app.growingseasons.server.repository;

import app.growingseasons.server.entity.HarvestingDates;

import java.util.Optional;

public interface HarvestingRepository extends BusinessKeyCheck<HarvestingDates, Long> {

    Optional<HarvestingDates> findByPlantId(long plantId);

}
