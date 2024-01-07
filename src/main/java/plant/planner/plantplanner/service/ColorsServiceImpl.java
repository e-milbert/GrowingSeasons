package plant.planner.plantplanner.service;

import org.springframework.stereotype.Service;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.ColorDto;
import plant.planner.plantplanner.entity.Colors;
import plant.planner.plantplanner.helpers.AttributesMapper;
import plant.planner.plantplanner.repository.ColorsRepository;
import plant.planner.plantplanner.service.interfaces.ColorsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColorsServiceImpl implements ColorsService {

    private final ColorsRepository colorsRepository;
    private final AttributesMapper mapper;

    public ColorsServiceImpl(ColorsRepository colorsRepository, AttributesMapper mapper) {
        this.colorsRepository = colorsRepository;
        this.mapper = mapper;
    }

    @Override
    public ColorDto getColor(int id) {
        Optional<Colors> opt = colorsRepository.findById(id);
        Colors color = opt.orElse(null);
        if (color != null) {
            return mapper.toColorDto(color);
        } else {
            return new ColorDto();
        }

    }

    @Override
    public ColorDto addNewColor(ColorDto newColor) {
        if (newColor.getId() == 0) {
            Colors updated = colorsRepository.save(mapper.toColors(newColor));
            return mapper.toColorDto(updated);
        } else {
            Logger.info("provided id was not 0");
            throw new IllegalArgumentException("must be without id, id must be 0");
        }

    }

    @Override
    public ColorDto updateColor(ColorDto updatedColor) throws IllegalArgumentException {
        if (updatedColor.getId() != 0) {
            Colors updated = colorsRepository.save(mapper.toColors(updatedColor));
            return mapper.toColorDto(updated);
        } else {
            Logger.info("provided id was 0");

            throw new IllegalArgumentException("new item with id 0 was provided. item must have id > 0");

        }
    }

    @Override
    public List<ColorDto> allColors() {
        List<Colors> colors = colorsRepository.findAll();
        List<ColorDto> dtos = new ArrayList<>();
        if (!colors.isEmpty()) {
            for (var color : colors) {
                dtos.add(mapper.toColorDto(color));
            }
        }
        return dtos;
    }

    @Override
    public boolean deleteColor(ColorDto oldDto) {
        try {
            Colors oldColor = mapper.toColors(oldDto);
            colorsRepository.delete(oldColor);
            return true;
        } catch (Exception ex) {
            Logger.info("delete unsuccessful");
            Logger.error(ex);
            return false;
        }
    }
}
