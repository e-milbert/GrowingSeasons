package app.growingseasons.server.repository;

import app.growingseasons.server.entity.SowingDates;

import java.util.Optional;

public interface SowingRepository extends BusinessKeyCheck<SowingDates, Long> {

    Optional<SowingDates> findByPlantId(long plantId);

}
