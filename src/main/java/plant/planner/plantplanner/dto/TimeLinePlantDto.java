package plant.planner.plantplanner.dto;

import java.util.List;

public class TimeLinePlantDto {

    private long plantId;
    private String name;
    private String officialName;
    private List<Integer> sowing;
    private long sowingId;
    private List<Integer> planting;
    private long plantingId;
    private List<Integer> bloom;
    private long bloomingId;
    private List<Integer> harvest;
    private long harvestId;

    public TimeLinePlantDto() {
    }

    public TimeLinePlantDto(
            long plantId,
            String name,
            String officialName,
            List<Integer> sowing,
            List<Integer> planting,
            List<Integer> bloom,
            List<Integer> harvest) {
        this.plantId = plantId;
        this.name = name;
        this.officialName=officialName;
        this.sowing = sowing;
        this.planting = planting;
        this.bloom = bloom;
        this.harvest = harvest;

    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public long getSowingId() {
        return sowingId;
    }

    public void setSowingId(long sowingId) {
        this.sowingId = sowingId;
    }

    public long getPlantingId() {
        return plantingId;
    }

    public void setPlantingId(long plantingId) {
        this.plantingId = plantingId;
    }

    public long getBloomingId() {
        return bloomingId;
    }

    public void setBloomingId(long bloomingId) {
        this.bloomingId = bloomingId;
    }

    public long getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(long harvestId) {
        this.harvestId = harvestId;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSowing() {
        return sowing;
    }

    public void setSowing(List<Integer> sowing) {
        this.sowing = sowing;
    }

    public List<Integer> getPlanting() {
        return planting;
    }

    public void setPlanting(List<Integer> planting) {
        this.planting = planting;
    }

    public List<Integer> getBloom() {
        return bloom;
    }

    public void setBloom(List<Integer> bloom) {
        this.bloom = bloom;
    }

    public List<Integer> getHarvest() {
        return harvest;
    }

    public void setHarvest(List<Integer> harvest) {
        this.harvest = harvest;
    }
}
