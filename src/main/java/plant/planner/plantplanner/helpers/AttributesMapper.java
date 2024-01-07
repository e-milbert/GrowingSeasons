package plant.planner.plantplanner.helpers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plant.planner.plantplanner.dto.*;
import plant.planner.plantplanner.entity.*;

@Mapper(componentModel = "spring")
public interface AttributesMapper {

    AttributesMapper INSTANCE = Mappers.getMapper(AttributesMapper.class);


    ColorDto toColorDto(Colors color);

    Colors toColors(ColorDto dto);

    SoilDto toSoilDto(Soil soil);

    Soil toSoil(SoilDto dto);

    ExposureLevelDto toExposureDto(ExposureLevel level);

    ExposureLevel toExposureLevel(ExposureLevelDto dto);

    GerminationTempDto toGerminationTempDto(GerminationTemp germinationTemp);

    GerminationTemp toGerminationTemp(GerminationTempDto dto);

    SizeDto toSizeDto(GrowthSize size);

    GrowthSize toGrowthSize(SizeDto dto);

}
