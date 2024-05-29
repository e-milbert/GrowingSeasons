package app.growingseasons.server.dto;

public class ExposureLevelDto {
    private int id = 0;
    private String type;

    public ExposureLevelDto() {
    }

    public ExposureLevelDto(int id, String type) {
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
