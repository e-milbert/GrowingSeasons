package app.growingseasons.server.helpers;

import app.growingseasons.server.dto.WeatherSettings;
import app.growingseasons.server.entity.WeatherPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WeatherSettingsMapper {
    ActionTimeLineMapper INSTANCE = Mappers.getMapper(ActionTimeLineMapper.class);

    WeatherSettings toWeatherSettings(WeatherPreferences weatherPreferences);

    WeatherPreferences toWeatherPreferences(WeatherSettings settings);


}
