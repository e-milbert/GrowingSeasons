package plant.planner.plantplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import plant.planner.plantplanner.dto.WeatherSettings;
import plant.planner.plantplanner.entity.WeatherPreferences;
import plant.planner.plantplanner.helpers.NetworkConnChecker;
import plant.planner.plantplanner.helpers.WeatherSettingsMapper;
import plant.planner.plantplanner.helpers.WeatherSettingsMapperImpl;
import plant.planner.plantplanner.repository.WeatherPreferencesRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    // TODO this test will not work if there is no internet connection or if you have a proxy in place
    private final WeatherSettingsMapper mapper = new WeatherSettingsMapperImpl();
    @InjectMocks
    private WeatherServiceImpl weatherService;
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherPreferencesRepository repository;

    private Map<String, Object> mockResponse;

    private WeatherPreferences defaultSettings;

    @BeforeEach
    public void setUp() throws IOException {
        weatherService = new WeatherServiceImpl(repository, mapper);

        defaultSettings = new WeatherPreferences();
        defaultSettings.setLatitude(4.5f);
        defaultSettings.setLongitude(50.98f);

    }


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


        try (MockedStatic<NetworkConnChecker> mockedStatic = Mockito.mockStatic(NetworkConnChecker.class)) {
            mockedStatic.when(NetworkConnChecker::hasInternet).thenReturn(true);

            weatherService.getExternalData();

            assertFalse(weatherService.getWeatherData().isEmpty());
            assertNotNull(weatherService.getCurrentWeather());
        } catch (IOException e) {

        }
    }

    @Test
    public void shouldReturnDefaultSettings() throws IOException {

        WeatherSettings s = new WeatherSettings(4.55f, 70.25f);

        when(repository.save(any(WeatherPreferences.class))).thenReturn(mapper.toWeatherPreferences(s));
        weatherService.updateSettings(s);
        String url = weatherService.getWeatherApi();


        assertTrue(url.contains(String.format(Locale.US, "%.2f", s.getLongitude())));
        assertTrue(url.contains(String.format(Locale.US, "%.2f", s.getLatitude())));


    }

}


