package app.growingseasons.server.service.interfaces;

import app.growingseasons.server.dto.SoilDto;

import java.util.List;

public interface SoilService {
    SoilDto getSoil(int id);

    SoilDto addNewSoil(SoilDto newSoil);

    SoilDto updateSoil(SoilDto updatedSoil);

    List<SoilDto> allSoils();

    boolean deleteSoil(SoilDto oldSoil);
}
