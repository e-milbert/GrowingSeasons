package plant.planner.plantplanner.dto;

public class SoilDto {
    private int id = 0;
    private String type;

    public SoilDto() {
    }

    public SoilDto(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
