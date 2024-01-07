package plant.planner.plantplanner.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.CurrentWeatherData;
import plant.planner.plantplanner.dto.DailyWeatherData;
import plant.planner.plantplanner.dto.SoilSixDaysData;
import plant.planner.plantplanner.helpers.NetworkConnChecker;
import plant.planner.plantplanner.service.interfaces.WeatherService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    RestTemplate restTemplate = new RestTemplate();

    //exchange longitude and latitude in url from Brandenburger Tor to own garden
    private String weatherApi = "https://api.open-meteo.com/v1/forecast?latitude=52.51&longitude=13.37&current=temperature_2m,rain,showers,snowfall,cloud_cover,wind_speed_10m,wind_direction_10m,wind_gusts_10m&hourly=temperature_2m,relative_humidity_2m,rain,showers,snowfall,soil_temperature_0cm,soil_temperature_6cm,soil_moisture_0_to_1cm,soil_moisture_3_to_9cm&daily=temperature_2m_max,temperature_2m_min,sunshine_duration,uv_index_max,rain_sum,showers_sum,snowfall_sum,wind_speed_10m_max,wind_gusts_10m_max&timezone=Europe/Berlin";
    private Map<String, Object> weatherData = new HashMap<>();

    public WeatherServiceImpl() {
        getExternalData();
    }


    public Map<String, Object> getWeatherData() {
        return weatherData;
    }

    private void setWeatherData(Map<String, Object> weatherData) {
        this.weatherData = weatherData;

    }

    public void getExternalData() {

        if (NetworkConnChecker.hasInternet()) {
            try {
                ResponseEntity<Map> response = restTemplate.getForEntity(weatherApi, Map.class);

                if (response.getStatusCode() == HttpStatus.OK) {

                    if (response.hasBody()) {
                        Map<String, Object> result = response.getBody();
                        setWeatherData(result);
                    }
                }
            } catch (Exception ex) {
                Logger.error(ex);
            }
        }
    }

    public CurrentWeatherData getCurrentWeather() {


        if (!getWeatherData().isEmpty() && getWeatherData().containsKey("current") && getWeatherData().containsKey("current_units")) {
            Map<String, Object> wData = (Map<String, Object>) getWeatherData().get("current");
            Map<String, Object> wUnits = (Map<String, Object>) getWeatherData().get("current_units");
            CurrentWeatherData cwd = new CurrentWeatherData(wData, wUnits);

            return cwd;
        }
        return null;
    }

    public SoilSixDaysData getSoilTemperatures() {
        if (!getWeatherData().isEmpty()) {
            try {
                Map<String, Object> hourlyData = (Map<String, Object>) getWeatherData().get("hourly");
                List<Double> soil0 = (ArrayList<Double>) hourlyData.get("soil_temperature_0cm");
                List<Double> soil6 = (ArrayList<Double>) hourlyData.get("soil_temperature_6cm");
                List<Double> moist1 = (ArrayList<Double>) hourlyData.get("soil_moisture_0_to_1cm");
                List<Double> moist2 = (ArrayList<Double>) hourlyData.get("soil_moisture_3_to_9cm");

                return new SoilSixDaysData(soil0, soil6, moist1, moist2);
            } catch (Exception ex) {
                Logger.error(ex);
            }

        }
        return null;
    }

    public List<DailyWeatherData> getForecastWeek() {
        if (!getWeatherData().isEmpty()) {
            try {
                Map<String, Object> dailyData = (Map<String, Object>) getWeatherData().get("daily");
                List<String> dates = (List<String>) dailyData.get("time");
                List<Double> maxTemps = (List<Double>) dailyData.get("temperature_2m_max");
                List<Double> minTemps = (List<Double>) dailyData.get("temperature_2m_min");
                List<Double> sunshine = new ArrayList<>();
                for (var e : (List<Double>) dailyData.get("temperature_2m_max")) {
                    var hrs = e / 3600;
                    sunshine.add(Math.round(hrs + 10) / 10.0);
                }
                List<Double> uv = (List<Double>) dailyData.get("uv_index_max");
                List<Double> rain = (List<Double>) dailyData.get("rain_sum");
                List<Double> showers = (List<Double>) dailyData.get("showers_sum");
                List<Double> snowfall = (List<Double>) dailyData.get("snowfall_sum");
                List<Double> wind = (List<Double>) dailyData.get("wind_speed_10m_max");
                List<Double> gusts = (List<Double>) dailyData.get("wind_gusts_10m_max");

                List<DailyWeatherData> data = new ArrayList<>();


                for (int i = 0; i < dates.size(); i++) {
                    data.add(new DailyWeatherData(
                            dates.get(i),
                            maxTemps.get(i),
                            minTemps.get(i),
                            sunshine.get(i),
                            uv.get(i),
                            rain.get(i),
                            showers.get(i),
                            snowfall.get(i),
                            wind.get(i),
                            gusts.get(i)
                    ));
                }
                return data;

            } catch (Exception ex) {
                Logger.error(ex);
            }

        }
        return null;
    }

    public String getWeatherApi() {
        return weatherApi;
    }

    public void setWeatherApi(String weatherApi) {
        this.weatherApi = weatherApi;
    }

}





