package app.growingseasons.server.dto;

public class WeatherSettings {

    private long id;
    private float longitude;
    private float latitude;


    public WeatherSettings() {
    }

    public WeatherSettings(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


}
