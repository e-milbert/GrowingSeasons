package app.growingseasons.server.dto;

import java.util.List;

public class PlantPlainDto {

    public List<String> plantAttributes;
    private long id;
    private String commonName;
    private String publicName;
    private List<String> colors;


    private int maxHeight;

    private int maxWidth;

    private List<String> exposure;


    private List<String> soil;


    private String germTemp;

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

    public List<String> getPlantAttributes() {
        return plantAttributes;
    }

    public void setPlantAttributes(List<String> plantAttributes) {
        this.plantAttributes = plantAttributes;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public List<String> getExposure() {
        return exposure;
    }

    public void setExposure(List<String> exposure) {
        this.exposure = exposure;
    }

    public List<String> getSoil() {
        return soil;
    }

    public void setSoil(List<String> soil) {
        this.soil = soil;
    }

    public String getGermTemp() {
        return germTemp;
    }

    public void setGermTemp(String germTemp) {
        this.germTemp = germTemp;
    }
}
