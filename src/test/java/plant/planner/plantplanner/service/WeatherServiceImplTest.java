package plant.planner.plantplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import plant.planner.plantplanner.dto.WeatherSettings;
import plant.planner.plantplanner.helpers.NetworkConnChecker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    // INFO this test will not work if there is no internet connection or if you have a proxy in place

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherServiceImpl weatherService;


    private Map<String, Object> mockResponse;


    @Test
    public void testSetWeatherDataWithInternetConnection() {
        mockResponse = new HashMap<>();
        Map<String, Object> currentData = new HashMap<>();
        currentData.put("temperature_2m", 15.5);
        currentData.put("rain", 0.0);
        // ... other fields ...

        Map<String, Object> currentUnits = new HashMap<>();
        currentUnits.put("temperature_2m", "°C");
        currentUnits.put("rain", "mm");
        // ... other fields ...

        mockResponse.put("current", currentData);
        mockResponse.put("current_units", currentUnits);

        ResponseEntity<Map> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(anyString(), Mockito.eq(Map.class))).thenReturn(responseEntity);

        try (MockedStatic<NetworkConnChecker> mockedStatic = Mockito.mockStatic(NetworkConnChecker.class)) {
            mockedStatic.when(NetworkConnChecker::hasInternet).thenReturn(true);

            weatherService.getExternalData();

            assertFalse(weatherService.getWeatherData().isEmpty());
            assertNotNull(weatherService.getCurrentWeather());
        } catch (IOException e) {

        }
    }

    @Test
    public void shouldUpdateUrlWithNewValues() throws IOException {

        WeatherSettings s = new WeatherSettings(4.55f,70.25f,"Pacific/Auckland");

        weatherService.updateSettings(s);
        String url = weatherService.getWeatherApi();


        assertTrue(url.contains(s.getTimezone()));
        assertTrue(url.contains(String.format(Locale.US,"%.2f",s.getLongitude())));
        assertTrue(url.contains(String.format(Locale.US,"%.2f",s.getLatitude())));


    }

}


