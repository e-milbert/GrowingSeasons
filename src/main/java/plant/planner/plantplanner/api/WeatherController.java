package plant.planner.plantplanner.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import plant.planner.plantplanner.dto.CurrentWeatherData;
import plant.planner.plantplanner.dto.DailyWeatherData;
import plant.planner.plantplanner.dto.SoilSixDaysData;
import plant.planner.plantplanner.service.interfaces.WeatherService;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/current")
    public ResponseEntity<?> getCurrent() {
        String body = "";
        try {
            CurrentWeatherData current = weatherService.getCurrentWeather();
            if (current != null) {
                return new ResponseEntity<>(current, HttpStatus.OK);
            }
        } catch (Exception ex) {
            body = Arrays.toString(ex.getStackTrace());
        }
        return ResponseEntity
                .internalServerError()
                .body(body);
    }

    @GetMapping("/weather/soil")
    public ResponseEntity<?> getSoilTemps() {
        String body = "";
        try {
            SoilSixDaysData data = weatherService.getSoilTemperatures();
            if (data != null) {
                return new ResponseEntity<>(data, HttpStatus.OK);
            }
        } catch (Exception ex) {
            body = Arrays.toString(ex.getStackTrace());
        }
        return ResponseEntity
                .internalServerError()
                .body(body);
    }

    @GetMapping("/weather/forecast")
    public ResponseEntity<?> getForecast() {
        String body = "";
        try {
            List<DailyWeatherData> data = weatherService.getForecastWeek();
            if (data != null) {
                return new ResponseEntity<>(data, HttpStatus.OK);
            }
        } catch (Exception ex) {
            body = Arrays.toString(ex.getStackTrace());
        }
        return ResponseEntity
                .internalServerError()
                .body(body);
    }


}
