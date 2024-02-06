package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.CurrentWeatherData;
import plant.planner.plantplanner.dto.DailyWeatherData;
import plant.planner.plantplanner.dto.SoilSixDaysData;
import plant.planner.plantplanner.dto.WeatherSettings;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WeatherService {
    Map<String, Object> getWeatherData();

    void getExternalData() throws IOException;

    CurrentWeatherData getCurrentWeather();

    SoilSixDaysData getSoilTemperatures();

    List<DailyWeatherData> getForecastWeek();

    void updateSettings(WeatherSettings settings) throws IOException;


}
