package plant.planner.plantplanner.helpers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plant.planner.plantplanner.dto.WeatherSettings;
import plant.planner.plantplanner.entity.WeatherPreferences;

@Mapper(componentModel = "spring")
public interface WeatherSettingsMapper {
    ActionTimeLineMapper INSTANCE = Mappers.getMapper(ActionTimeLineMapper.class);

    WeatherSettings toWeatherSettings(WeatherPreferences weatherPreferences);

    WeatherPreferences toWeatherPreferences(WeatherSettings settings);


}
