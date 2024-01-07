package plant.planner.plantplanner.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "planting")
public class PlantingDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String businessKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    private String timeLineAsJson;

    public PlantingDates() {
        this.businessKey = generateBusinessKey();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantingDates plantingDates = (PlantingDates) o;
        return Objects.equals(businessKey, plantingDates.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessKey);
    }

    private String generateBusinessKey() {
        return UUID.randomUUID().toString();
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

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public String getTimeLineAsJson() {
        return timeLineAsJson;
    }

    public void setTimeLineAsJson(String timeLine) {
        this.timeLineAsJson = timeLine;
    }
}


