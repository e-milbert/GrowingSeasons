package plant.planner.plantplanner.dto;

public class SizeDto {
    private int id = 0;

    private int size;

    public SizeDto() {
    }

    public SizeDto(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
