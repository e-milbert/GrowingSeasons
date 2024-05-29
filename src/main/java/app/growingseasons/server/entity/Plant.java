package app.growingseasons.server.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String businessKey;
    private String commonName;
    private String publicName;

    private boolean edible;
    private boolean ornamental;
    private boolean poisonous;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "colors_plants",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<Colors> colors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "max_height_id")
    private GrowthSize maxHeight;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "max_width_id")
    private GrowthSize maxWidth;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "sun_exposure_levels_plants",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "exposure_level_id"))
    private List<ExposureLevel> exposureLevels;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "soil_types_plants",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "soil_id"))
    private List<Soil> soilTypes;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "germinationTemp_id")
    private GerminationTemp germinationTempRange;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FloweringDates> floweringDates = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestingDates> harvestingDates = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantingDates> plantingDates = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SowingDates> sowingDates = new ArrayList<>();

    public Plant() {
        this.businessKey = generateBusinessKey();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Objects.equals(businessKey, plant.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessKey);
    }

    public List<FloweringDates> getFloweringDates() {
        return floweringDates;
    }

    public void setFloweringDates(List<FloweringDates> floweringDates) {
        this.floweringDates = floweringDates;
    }

    public List<HarvestingDates> getHarvestingDates() {
        return harvestingDates;
    }

    public void setHarvestingDates(List<HarvestingDates> harvestingDates) {
        this.harvestingDates = harvestingDates;
    }

    public List<PlantingDates> getPlantingDates() {
        return plantingDates;
    }

    public void setPlantingDates(List<PlantingDates> plantingDates) {
        this.plantingDates = plantingDates;
    }

    public List<SowingDates> getSowingDates() {
        return sowingDates;
    }

    public void setSowingDates(List<SowingDates> sowingDates) {
        this.sowingDates = sowingDates;
    }

    private String generateBusinessKey() {
        return UUID.randomUUID().toString();
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public boolean isEdible() {
        return edible;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public boolean isOrnamental() {
        return ornamental;
    }

    public void setOrnamental(boolean ornamental) {
        this.ornamental = ornamental;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    public List<Colors> getColors() {
        return colors;
    }

    public void setColors(List<Colors> colors) {
        this.colors = colors;
    }

    public GrowthSize getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(GrowthSize maxHeight) {
        this.maxHeight = maxHeight;
    }

    public GrowthSize getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(GrowthSize maxWidth) {
        this.maxWidth = maxWidth;
    }

    public List<ExposureLevel> getExposureLevels() {
        return exposureLevels;
    }

    public void setExposureLevels(List<ExposureLevel> exposureLevels) {
        this.exposureLevels = exposureLevels;
    }

    public List<Soil> getSoilTypes() {
        return soilTypes;
    }

    public void setSoilTypes(List<Soil> soilTypes) {
        this.soilTypes = soilTypes;
    }

    public GerminationTemp getGerminationTempRange() {
        return germinationTempRange;
    }

    public void setGerminationTempRange(GerminationTemp germinationTempRange) {
        this.germinationTempRange = germinationTempRange;
    }

}
