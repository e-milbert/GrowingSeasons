package plant.planner.plantplanner.service;


import org.springframework.stereotype.Service;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.GerminationTempDto;
import plant.planner.plantplanner.entity.GerminationTemp;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.repository.GerminationTempRepository;
import plant.planner.plantplanner.service.interfaces.GerminationTempService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GerminationTempServiceImpl implements GerminationTempService {

    private final GerminationTempRepository germinationTempRepository;
    private final AttributesMapper mapper;

    public GerminationTempServiceImpl(GerminationTempRepository germinationTempRepository, AttributesMapper mapper) {
        this.germinationTempRepository = germinationTempRepository;
        this.mapper = mapper;
    }


    /***
     *
     * @param id
     * @return GerminationTempDto or NULL
     */
    @Override
    public GerminationTempDto getGerminationTemp(long id) {
        Optional<GerminationTemp> opt = germinationTempRepository.findById(id);
        GerminationTemp result = opt.orElse(null);
        if (result != null) {
            return mapper.toGerminationTempDto(result);

        } else {
            return null;
        }
    }

    @Override
    public GerminationTempDto addNewGerminationTemp(GerminationTempDto newGerminationTemp) throws IllegalArgumentException {
        if (newGerminationTemp.getId() == 0 && checkMinMaxTemperatures(newGerminationTemp)) {
            GerminationTemp updated = germinationTempRepository.save(mapper.toGerminationTemp(newGerminationTemp));
            return mapper.toGerminationTempDto(updated);
        } else {
            Logger.info("provided id must be 0 and min temperature must be smaller");
            throw new IllegalArgumentException("invalid germinationTemp object");
        }
    }

    @Override
    public GerminationTempDto updateGerminationTemp(GerminationTempDto updatedGerminationTemp) throws IllegalArgumentException {
        if (updatedGerminationTemp.getId() != 0 && checkMinMaxTemperatures(updatedGerminationTemp)) {

            GerminationTemp updated = germinationTempRepository.save(mapper.toGerminationTemp(updatedGerminationTemp));
            return mapper.toGerminationTempDto(updated);
        } else {
            Logger.info("provided id must be > 0 and min temperature must be smaller");
            throw new IllegalArgumentException("invalid germinationTemp object");
        }
    }

    private boolean checkMinMaxTemperatures(GerminationTempDto updatedGerminationTemp) {

        return updatedGerminationTemp.getMaxGerminationTemp() > updatedGerminationTemp.getMinGerminationTemp();
    }

    @Override
    public List<GerminationTempDto> allGerminationTemps() {
        List<GerminationTemp> result = germinationTempRepository.findAll();
        List<GerminationTempDto> dtos = new ArrayList<>();
        if (!result.isEmpty()) {
            for (var entry : result) {
                dtos.add(mapper.toGerminationTempDto(entry));
            }
        }
        return dtos;
    }

    @Override
    public boolean deleteGerminationTemp(GerminationTempDto oldGerminationTemp) {
        try {
            GerminationTemp old = mapper.toGerminationTemp(oldGerminationTemp);
            germinationTempRepository.delete(old);
            return true;
        } catch (Exception ex) {
            Logger.info("delete unsuccessful");
            Logger.error(ex);
            return false;
        }
    }
}
