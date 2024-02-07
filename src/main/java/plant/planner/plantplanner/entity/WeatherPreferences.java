package plant.planner.plantplanner.entity;

import jakarta.persistence.*;

import java.util.Objects;

import static plant.planner.plantplanner.helpers.BusinessKeyGen.generateBusinessKey;

@Entity
public class WeatherPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String businessKey;

    private float longitude; //-180 - +180
    private float latitude;//-90 - +90


    public WeatherPreferences() {
        this.businessKey = generateBusinessKey();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherPreferences weatherPreferences = (WeatherPreferences) o;
        return Objects.equals(businessKey, weatherPreferences.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessKey);
    }
}
