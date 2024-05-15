package app.growingseasons.server.helpers;

import app.growingseasons.server.dto.*;
import app.growingseasons.server.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
