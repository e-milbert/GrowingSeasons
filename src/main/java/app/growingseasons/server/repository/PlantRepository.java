package app.growingseasons.server.repository;

import app.growingseasons.server.entity.Plant;

public interface PlantRepository extends BusinessKeyCheck<Plant, Long> {
    boolean existsById(long id);
}
