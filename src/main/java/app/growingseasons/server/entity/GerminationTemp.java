package app.growingseasons.server.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "germination_temperatures")
public class GerminationTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String businessKey;
    @OneToOne(mappedBy = "germinationTempRange")
    private Plant plant;
    private int minGerminationTemp;
    private int maxGerminationTemp;

    public GerminationTemp() {

        this.businessKey = generateBusinessKey();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GerminationTemp germinationTemp = (GerminationTemp) o;
        return Objects.equals(businessKey, germinationTemp.businessKey);
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

    public int getMinGerminationTemp() {
        return minGerminationTemp;
    }

    public void setMinGerminationTemp(int minGerminationTemp) {
        this.minGerminationTemp = minGerminationTemp;
    }

    public int getMaxGerminationTemp() {
        return maxGerminationTemp;
    }

    public void setMaxGerminationTemp(int maxGerminationTemp) {
        this.maxGerminationTemp = maxGerminationTemp;
    }
}
