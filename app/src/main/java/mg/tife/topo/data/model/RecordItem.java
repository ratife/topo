package mg.tife.topo.data.model;

public class RecordItem {
    private Long id;
    private Float angle;
    private Float distance;
    private String observation;
    private Long recordId;

    public RecordItem(Long id,Float angle, Float distance, String observation, Long recordId) {
        this.id = id;
        this.angle = angle;
        this.distance = distance;
        this.observation = observation;
        this.recordId = recordId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAngle() {
        return angle;
    }

    public void setAngle(Float angle) {
        this.angle = angle;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
