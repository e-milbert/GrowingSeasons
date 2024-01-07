package plant.planner.plantplanner.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ExposureLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String businessKey;
    private String type;

    @ManyToMany(mappedBy = "exposureLevels")
    private List<Plant> plants;

    public ExposureLevel() {
        this.businessKey = generateBusinessKey();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExposureLevel level = (ExposureLevel) o;
        return Objects.equals(businessKey, level.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessKey);
    }

    private String generateBusinessKey() {
        return UUID.randomUUID().toString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
