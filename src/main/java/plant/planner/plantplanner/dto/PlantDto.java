package plant.planner.plantplanner.dto;

import java.util.List;

public class PlantDto {

    private long id = 0;
    private String commonName;
    private String publicName;

    private boolean edible;
    private boolean ornamental;
    private boolean poisonous;


    private List<ColorDto> colors;


    private SizeDto maxHeight;

    private SizeDto maxWidth;

    private List<ExposureLevelDto> exposureLevels;


    private List<SoilDto> soilTypes;


    private GerminationTempDto germinationTempRange;

    public PlantDto() {
    }

    public PlantDto(long id, String commonName, String publicName, boolean edible, boolean ornamental, boolean poisonous, List<ColorDto> colors, SizeDto maxHeight, SizeDto maxWidth, List<ExposureLevelDto> exposureLevels, List<SoilDto> soilTypes, GerminationTempDto germinationTempRange) {
        this.id = id;
        this.commonName = commonName;
        this.publicName = publicName;
        this.edible = edible;
        this.ornamental = ornamental;
        this.poisonous = poisonous;
        this.colors = colors;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.exposureLevels = exposureLevels;
        this.soilTypes = soilTypes;
        this.germinationTempRange = germinationTempRange;
    }

    public PlantDto(String commonName, String publicName, boolean edible, boolean ornamental, boolean poisonous, List<ColorDto> colors, SizeDto maxHeight, SizeDto maxWidth, List<ExposureLevelDto> exposureLevels, List<SoilDto> soilTypes, GerminationTempDto germinationTempRange) {
        this.id = 0;
        this.commonName = commonName;
        this.publicName = publicName;
        this.edible = edible;
        this.ornamental = ornamental;
        this.poisonous = poisonous;
        this.colors = colors;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.exposureLevels = exposureLevels;
        this.soilTypes = soilTypes;
        this.germinationTempRange = germinationTempRange;
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

    public List<ColorDto> getColors() {
        return colors;
    }

    public void setColors(List<ColorDto> colors) {
        this.colors = colors;
    }

    public SizeDto getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(SizeDto maxHeight) {
        this.maxHeight = maxHeight;
    }

    public SizeDto getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(SizeDto maxWidth) {
        this.maxWidth = maxWidth;
    }

    public List<ExposureLevelDto> getExposureLevels() {
        return exposureLevels;
    }

    public void setExposureLevels(List<ExposureLevelDto> exposureLevels) {
        this.exposureLevels = exposureLevels;
    }

    public List<SoilDto> getSoilTypes() {
        return soilTypes;
    }

    public void setSoilTypes(List<SoilDto> soilTypes) {
        this.soilTypes = soilTypes;
    }

    public GerminationTempDto getGerminationTempRange() {
        return germinationTempRange;
    }

    public void setGerminationTempRange(GerminationTempDto germinationTempRange) {
        this.germinationTempRange = germinationTempRange;
    }
}
