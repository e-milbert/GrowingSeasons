package app.growingseasons.server.service;

import app.growingseasons.server.dto.ExposureLevelDto;
import app.growingseasons.server.entity.ExposureLevel;
import app.growingseasons.server.helpers.AttributesMapper;
import app.growingseasons.server.repository.ExposureLevelRepository;
import app.growingseasons.server.service.interfaces.ExposureLevelService;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExposureLevelServiceImpl implements ExposureLevelService {

    private final ExposureLevelRepository exposureLevelRepository;
    private final AttributesMapper mapper;

    public ExposureLevelServiceImpl(ExposureLevelRepository exposureLevelRepository, AttributesMapper mapper) {
        this.exposureLevelRepository = exposureLevelRepository;
        this.mapper = mapper;
    }

    @Override
    public ExposureLevelDto getExposureLevel(int id) {
        Optional<ExposureLevel> opt = exposureLevelRepository.findById(id);
        ExposureLevel result = opt.orElse(null);
        if (result != null) {
            return mapper.toExposureDto(result);
        } else {
            return new ExposureLevelDto();
        }
    }

    @Override
    public ExposureLevelDto addNewExposureLevel(ExposureLevelDto newLevel) throws IllegalArgumentException {
        if (newLevel.getId() == 0) {
            ExposureLevel newL = mapper.toExposureLevel(newLevel);
            ExposureLevel addedLevel = exposureLevelRepository.save(newL);
            return mapper.toExposureDto(addedLevel);
        } else {
            Logger.info("provided id was not 0");
            throw new IllegalArgumentException("must be without id, id must be 0");
        }
    }

    @Override
    public ExposureLevelDto updateExposureLevel(ExposureLevelDto updatedLevel) throws IllegalArgumentException {
        if (updatedLevel.getId() != 0) {
            ExposureLevel toUpdate = mapper.toExposureLevel(updatedLevel);
            ExposureLevel updated = exposureLevelRepository.save(toUpdate);
            return mapper.toExposureDto(updated);
        } else {
            Logger.info("provided id was 0");

            throw new IllegalArgumentException("new item with id 0 was provided. item must have id > 0");
        }
    }

    @Override
    public List<ExposureLevelDto> allExposureLevels() {
        List<ExposureLevel> levels = exposureLevelRepository.findAll();
        List<ExposureLevelDto> dtos = new ArrayList<>();
        if (!levels.isEmpty()) {
            for (var level : levels) {
                dtos.add(mapper.toExposureDto(level));
            }
        }
        return dtos;
    }

    @Override
    public boolean deleteExposureLevel(ExposureLevelDto oldExposureLevel) {
        try {
            ExposureLevel old = mapper.toExposureLevel(oldExposureLevel);
            exposureLevelRepository.delete(old);
            return true;
        } catch (Exception ex) {
            Logger.info("delete unsuccessful");
            Logger.error(ex);
            return false;
        }
    }
}
