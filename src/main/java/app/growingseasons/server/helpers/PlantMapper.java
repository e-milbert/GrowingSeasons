package app.growingseasons.server.helpers;

import app.growingseasons.server.dto.PlantDto;
import app.growingseasons.server.dto.PlantPlainDto;
import app.growingseasons.server.entity.Plant;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlantMapper {

    PlantMapper INSTANCE = Mappers.getMapper(PlantMapper.class);

    Plant PlantDtoToPlant(PlantDto dto, @Context AttributesMapper attributesMapper);


    PlantDto PlantToPlantDto(Plant plant, @Context AttributesMapper attributesMapper);

    @Mapping(target = "colors", ignore = true)
    @Mapping(target = "exposure", ignore = true)
    @Mapping(target = "soil", ignore = true)
    @Mapping(target = "maxHeight", ignore = true)
    @Mapping(target = "maxWidth", ignore = true)
    PlantPlainDto toPlantPlainDto(Plant plant);

    @AfterMapping
    default void afterMapping(Plant plant, @MappingTarget PlantPlainDto dto) {
        List<String> colors = new ArrayList<>();
        if (!plant.getColors().isEmpty()) {
            for (var color : plant.getColors()) {
                colors.add(color.getName());
            }
        }
        dto.setColors(colors);

        List<String> expo = new ArrayList<>();
        if (!plant.getExposureLevels().isEmpty()) {
            for (var exposure : plant.getExposureLevels()) {
                expo.add(exposure.getType());
            }
        }
        dto.setExposure(expo);

        List<String> soil = new ArrayList<>();
        if (!plant.getSoilTypes().isEmpty()) {
            for (var soi : plant.getSoilTypes()) {
                soil.add(soi.getType());
            }
        }
        dto.setSoil(soil);

        List<String> attrib = new ArrayList<>();
        if (plant.isEdible()) {
            attrib.add("edible");
        }
        if (plant.isOrnamental()) {
            attrib.add("ornamental");
        }
        if (plant.isPoisonous()) {
            attrib.add("poisonous");
        }
        dto.setPlantAttributes(attrib);

        dto.setMaxHeight(plant.getMaxHeight().getSize());
        dto.setMaxWidth(plant.getMaxWidth().getSize());

        dto.setGermTemp(String.format("%d - %d", plant.getGerminationTempRange().getMinGerminationTemp(), plant.getGerminationTempRange().getMaxGerminationTemp()));


    }

}
