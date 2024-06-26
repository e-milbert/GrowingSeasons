package app.growingseasons.server.service.interfaces;

import app.growingseasons.server.dto.ExposureLevelDto;

import java.util.List;

public interface ExposureLevelService {

    ExposureLevelDto getExposureLevel(int id);

    ExposureLevelDto addNewExposureLevel(ExposureLevelDto newLevel);

    ExposureLevelDto updateExposureLevel(ExposureLevelDto updatedLevel);

    List<ExposureLevelDto> allExposureLevels();

    boolean deleteExposureLevel(ExposureLevelDto oldExposureLevel);
}
