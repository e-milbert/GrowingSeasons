package app.growingseasons.server.service.interfaces;

import app.growingseasons.server.dto.SizeDto;

import java.util.List;

public interface GrowthSizeService {

    SizeDto getGrowthSize(int id);

    SizeDto addNewGrowthSize(SizeDto newGrowthSize);

    SizeDto updateGrowthSize(SizeDto updatedGrowthSize);

    List<SizeDto> allGrowthSizes();

    boolean deleteGrowthSize(SizeDto oldGrowthSize);

}
