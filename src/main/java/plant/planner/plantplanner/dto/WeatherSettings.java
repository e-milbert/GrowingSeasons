package plant.planner.plantplanner.dto;

public class WeatherSettings {

    private float longitude;
    private float latitude;
    private String timezone;

    public WeatherSettings() {
    }

    public WeatherSettings(float longitude, float latitude, String timezone) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.timezone = timezone;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
