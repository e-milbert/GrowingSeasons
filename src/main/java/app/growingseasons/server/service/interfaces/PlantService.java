package app.growingseasons.server.service.interfaces;

import app.growingseasons.server.dto.PlantDto;
import app.growingseasons.server.dto.PlantPlainDto;
import app.growingseasons.server.entity.Plant;

import java.util.List;

public interface PlantService {

    List<PlantPlainDto> allPlantPlainDtos();

    PlantDto getPlantDto(long id);

    Plant getPlant(long id);

    List<PlantDto> allPlantDtos();

    List<Plant> getAllPlants();

    boolean deletePlant(PlantDto dto);

    PlantDto updatePlant(PlantDto dto) throws IllegalArgumentException;

    PlantDto addNewPlant(PlantDto newPlant);

    boolean plantExists(long id);
}
