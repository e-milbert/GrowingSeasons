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
import plant.planner.plantplanner.helpers.NetworkConnChecker;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherServiceImpl weatherService;


    private Map<String, Object> mockResponse;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    public void testSetWeatherDataWithInternetConnection() {
        try (MockedStatic<NetworkConnChecker> mockedStatic = Mockito.mockStatic(NetworkConnChecker.class)) {
            mockedStatic.when(NetworkConnChecker::hasInternet).thenReturn(true);

            weatherService.getExternalData();

            assertFalse(weatherService.getWeatherData().isEmpty());
            assertNotNull(weatherService.getCurrentWeather());
        }
    }

}


