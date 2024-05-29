package app.growingseasons.server.dto;

public class GerminationTempDto {
    private long id = 0;
    private int minGerminationTemp;
    private int maxGerminationTemp;

    public GerminationTempDto() {
    }

    public GerminationTempDto(long id, int minGerminationTemp, int maxGerminationTemp) {
        this.id = id;
        this.minGerminationTemp = minGerminationTemp;
        this.maxGerminationTemp = maxGerminationTemp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
