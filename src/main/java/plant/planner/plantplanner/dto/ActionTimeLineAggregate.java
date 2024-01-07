package plant.planner.plantplanner.dto;

public class ActionTimeLineAggregate {

    private long plantId;
    private long actionId;
    private String actionType;
    private String jsonTimeline;

    public ActionTimeLineAggregate() {

    }

    public ActionTimeLineAggregate(long plantId, String actionType, String jsonTimeline) {


        this.plantId = plantId;

        this.actionType = actionType;
        this.jsonTimeline = jsonTimeline;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType.toLowerCase();
    }

    public String getJsonTimeline() {
        return jsonTimeline;
    }

    public void setJsonTimeline(String jsonTimeline) {
        this.jsonTimeline = jsonTimeline;
    }
}
