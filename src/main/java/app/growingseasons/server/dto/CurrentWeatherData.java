package app.growingseasons.server.dto;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrentWeatherData {

    private String time;// "2024-01-23T13:00",
    private String interval;//": 900,
    private String temperature_2m; //": 5.8,
    private String rain;//": 0.00,
    private String showers;//": 0.00,
    private String snowfall;//": 0.00,
    private String cloud_cover;//": 88,
    private String wind_speed_10m;//": 11.8,
    private String wind_direction_10m;//": 250,
    private String wind_gusts_10m;//": 27.7

    public CurrentWeatherData(Map<String, Object> data, Map<String, Object> units) {
        setAll(data, units);
    }

    private void setAll(Map<String, Object> data, Map<String, Object> units) {
        String date = safeFormat(data, "time", "");
        int ind = date.indexOf("T");
        ArrayList<Character> chars = new ArrayList<>();
        for (int i = ind + 1; i < date.length(); i++) {
            chars.add(date.toCharArray()[i]);
        }
        setTime(chars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining()));

        setInterval(safeFormat(data, "interval", units.get("interval")));
        setTemperature_2m(safeFormat(data, "temperature_2m", units.get("temperature_2m")));
        setRain(safeFormat(data, "rain", units.get("rain")));
        setShowers(safeFormat(data, "showers", units.get("showers")));
        setSnowfall(safeFormat(data, "snowfall", units.get("snowfall")));
        setCloud_cover(safeFormat(data, "cloud_cover", units.get("cloud_cover")));
        setWind_speed_10m(safeFormat(data, "wind_speed_10m", units.get("wind_speed_10m")));
        setWind_direction_10m(safeFormat(data, "wind_direction_10m", units.get("wind_direction_10m")));
        setWind_gusts_10m(safeFormat(data, "wind_gusts_10m", units.get("wind_gusts_10m")));
    }

    private String safeFormat(Map<String, Object> data, String key, Object unit) {
        Object value = data.get(key);
        if (value == null) {
            return "Unknown";
        }
        if (value instanceof Number) {
            return String.format("%s %s", value, unit);
        } else {
            return String.format("%s", value);
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(String temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getShowers() {
        return showers;
    }

    public void setShowers(String showers) {
        this.showers = showers;
    }

    public String getSnowfall() {
        return snowfall;
    }

    public void setSnowfall(String snowfall) {
        this.snowfall = snowfall;
    }

    public String getCloud_cover() {
        return cloud_cover;
    }

    public void setCloud_cover(String cloud_cover) {
        this.cloud_cover = cloud_cover;
    }

    public String getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(String wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public String getWind_direction_10m() {
        return wind_direction_10m;
    }

    public void setWind_direction_10m(String wind_direction_10m) {
        this.wind_direction_10m = wind_direction_10m;
    }

    public String getWind_gusts_10m() {
        return wind_gusts_10m;
    }

    public void setWind_gusts_10m(String wind_gusts_10m) {
        this.wind_gusts_10m = wind_gusts_10m;
    }
}
