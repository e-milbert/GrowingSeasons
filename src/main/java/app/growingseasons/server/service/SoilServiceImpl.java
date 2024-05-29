package app.growingseasons.server.service;

import app.growingseasons.server.dto.SoilDto;
import app.growingseasons.server.entity.Soil;
import app.growingseasons.server.helpers.AttributesMapper;
import app.growingseasons.server.repository.SoilRepository;
import app.growingseasons.server.service.interfaces.SoilService;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SoilServiceImpl implements SoilService {

    private final SoilRepository repository;
    private final AttributesMapper mapper;

    public SoilServiceImpl(SoilRepository repository, AttributesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SoilDto getSoil(int id) {
        Optional<Soil> opt = repository.findById(id);
        Soil soil = opt.orElse(null);
        if (soil != null) {
            return mapper.toSoilDto(soil);
        } else {
            return new SoilDto();
        }
    }

    @Override
    public SoilDto addNewSoil(SoilDto newSoil) throws IllegalArgumentException {
        if (newSoil.getId() == 0) {
            Soil updated = repository.save(mapper.toSoil(newSoil));
            return mapper.toSoilDto(updated);
        } else {
            Logger.info("provided id was not 0");
            throw new IllegalArgumentException("must be without id, id must be 0");
        }
    }

    @Override
    public SoilDto updateSoil(SoilDto updatedSoil) throws IllegalArgumentException {
        if (updatedSoil.getId() != 0) {
            Soil updated = repository.save(mapper.toSoil(updatedSoil));
            return mapper.toSoilDto(updated);
        } else {
            Logger.info("provided id was 0");

            throw new IllegalArgumentException("new item with id 0 was provided. item must have id > 0");
        }
    }

    @Override
    public List<SoilDto> allSoils() {
        List<Soil> soils = repository.findAll();
        List<SoilDto> dtos = new ArrayList<>();
        if (!soils.isEmpty()) {
            for (var soil : soils) {
                dtos.add(mapper.toSoilDto(soil));
            }
        }
        return dtos;
    }

    @Override
    public boolean deleteSoil(SoilDto oldSoil) {
        try {
            Soil old = mapper.toSoil(oldSoil);
            repository.delete(old);
            return true;
        } catch (Exception ex) {
            Logger.info("delete unsuccessful");
            Logger.error(ex);
            return false;
        }
    }
}
