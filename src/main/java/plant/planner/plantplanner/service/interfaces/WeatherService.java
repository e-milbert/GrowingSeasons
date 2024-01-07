package plant.planner.plantplanner.service.interfaces;

import plant.planner.plantplanner.dto.CurrentWeatherData;
import plant.planner.plantplanner.dto.DailyWeatherData;
import plant.planner.plantplanner.dto.SoilSixDaysData;

import java.util.List;
import java.util.Map;

public interface WeatherService {
    Map<String, Object> getWeatherData();

    void getExternalData();

    CurrentWeatherData getCurrentWeather();

    SoilSixDaysData getSoilTemperatures();

    List<DailyWeatherData> getForecastWeek();

}
