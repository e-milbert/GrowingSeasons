package app.growingseasons.server.service.interfaces;

import app.growingseasons.server.dto.CurrentWeatherData;
import app.growingseasons.server.dto.DailyWeatherData;
import app.growingseasons.server.dto.SoilSixDaysData;
import app.growingseasons.server.dto.WeatherSettings;

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
