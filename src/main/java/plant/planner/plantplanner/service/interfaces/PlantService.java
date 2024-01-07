package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.PlantDto;
import plant.planner.plantplanner.dto.PlantPlainDto;
import plant.planner.plantplanner.entity.Plant;

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
