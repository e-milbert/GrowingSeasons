package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.GerminationTempDto;

import java.util.List;

public interface GerminationTempService {
    GerminationTempDto getGerminationTemp(long id);

    GerminationTempDto addNewGerminationTemp(GerminationTempDto newGerminationTemp);

    GerminationTempDto updateGerminationTemp(GerminationTempDto updatedGerminationTemp);

    List<GerminationTempDto> allGerminationTemps();

    boolean deleteGerminationTemp(GerminationTempDto oldGerminationTemp);
}
