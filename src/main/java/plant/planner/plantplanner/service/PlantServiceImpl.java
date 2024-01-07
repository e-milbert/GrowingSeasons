package plant.planner.plantplanner.service;

import org.springframework.stereotype.Service;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.PlantDto;
import plant.planner.plantplanner.dto.PlantPlainDto;
import plant.planner.plantplanner.entity.Plant;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.helpers.BusinessKeyGen;
import plant.planner.plantplanner.helpers.PlantMapper;
import plant.planner.plantplanner.repository.PlantRepository;
import plant.planner.plantplanner.service.interfaces.PlantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantR;
    private final PlantMapper plantMapper;
    private final AttributesMapper attributesMapper;

    public PlantServiceImpl(PlantRepository plantR, PlantMapper plantMapper, AttributesMapper attributesMapper) {
        this.plantR = plantR;
        this.plantMapper = plantMapper;
        this.attributesMapper = attributesMapper;
    }


    @Override
    public Plant getPlant(long id) {
        Optional<Plant> opt = plantR.findById(id);
        return opt.orElse(null);
    }

    public PlantDto getPlantDto(long id) {
        Optional<Plant> opt = plantR.findById(id);
        Plant plant = opt.orElse(null);
        return plantMapper.PlantToPlantDto(plant, attributesMapper);
    }

    public List<PlantDto> allPlantDtos() {
        List<Plant> plants = plantR.findAll();
        List<PlantDto> dtos = new ArrayList<>();
        if (!plants.isEmpty()) {
            for (var plant : plants) {
                dtos.add(plantMapper.PlantToPlantDto(plant, attributesMapper));
            }
        }
        return dtos;
    }

    public List<PlantPlainDto> allPlantPlainDtos() {
        List<Plant> plants = plantR.findAll();
        List<PlantPlainDto> dtos = new ArrayList<>();
        if (!plants.isEmpty()) {
            for (var plant : plants) {
                dtos.add(plantMapper.toPlantPlainDto(plant));
            }
        }
        return dtos;
    }

    public List<Plant> getAllPlants() {
        List<Plant> plants = plantR.findAll();
        return plants;
    }

    public boolean deletePlant(PlantDto dto) {
        try {
            plantR.delete(plantMapper.PlantDtoToPlant(dto, attributesMapper));
            return true;
        } catch (Exception ex) {
            Logger.info("delete unsuccessful");
            Logger.error(ex);
            return false;
        }
    }

    public PlantDto updatePlant(PlantDto dto) throws IllegalArgumentException {
        Optional<Plant> opt = plantR.findById(dto.getId());
        Plant orig = opt.orElse(null);
        if (orig != null) {
            Plant updated = plantMapper.PlantDtoToPlant(dto, attributesMapper);
            plantR.save(updated);
            return plantMapper.PlantToPlantDto(updated, attributesMapper);
        } else {

            throw new IllegalArgumentException("was not able to retrieve plant from db");

        }

    }

    public PlantDto addNewPlant(PlantDto newPlant) {
        Plant plantToAdd = plantMapper.PlantDtoToPlant(newPlant, attributesMapper);
        while (plantR.existsByBusinessKey(plantToAdd.getBusinessKey())) {
            plantToAdd.setBusinessKey(BusinessKeyGen.generateBusinessKey());
        }
        Plant savedPlant = plantR.save(plantToAdd);
        return plantMapper.PlantToPlantDto(savedPlant, attributesMapper);
    }

    @Override
    public boolean plantExists(long id) {
        return plantR.existsById(id);
    }
}