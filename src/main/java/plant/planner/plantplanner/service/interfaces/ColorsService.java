package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.ColorDto;

import java.util.List;

public interface ColorsService {

    ColorDto getColor(int id);

    ColorDto addNewColor(ColorDto newColor);

    ColorDto updateColor(ColorDto updatedColor);

    List<ColorDto> allColors();

    boolean deleteColor(ColorDto oldDto);

}
