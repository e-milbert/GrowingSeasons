package plant.planner.plantplanner.api;

import org.apache.juli.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;
import plant.planner.plantplanner.dto.CurrentWeatherData;
import plant.planner.plantplanner.dto.DailyWeatherData;
import plant.planner.plantplanner.dto.SoilSixDaysData;
import plant.planner.plantplanner.dto.WeatherSettings;
import plant.planner.plantplanner.service.interfaces.WeatherService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/weather/update")
    public ResponseEntity<?> updateWeatherSettings(@RequestBody WeatherSettings newSettings){

        try{
            weatherService.updateSettings(newSettings);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (IllegalArgumentException ex){
            Logger.error(ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (IOException ex){
            Logger.error(ex);
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }


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
