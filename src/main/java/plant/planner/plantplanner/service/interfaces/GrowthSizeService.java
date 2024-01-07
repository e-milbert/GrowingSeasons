package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.SizeDto;

import java.util.List;

public interface GrowthSizeService {

    SizeDto getGrowthSize(int id);

    SizeDto addNewGrowthSize(SizeDto newGrowthSize);

    SizeDto updateGrowthSize(SizeDto updatedGrowthSize);

    List<SizeDto> allGrowthSizes();

    boolean deleteGrowthSize(SizeDto oldGrowthSize);

}
