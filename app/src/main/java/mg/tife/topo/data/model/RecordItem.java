package mg.tife.topo.data.model;

public class RecordItem {
    private Long id;
    private Float angleH;
    private Float angelV;
    private Float distance;
    private String stantion;
    private String observation;
    private Long recordId;

    public RecordItem(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAngleH() {
        return angleH;
    }

    public void setAngleH(Float angleH) {
        this.angleH = angleH;
    }

    public Float getAngelV() {
        return angelV;
    }

    public void setAngelV(Float angelV) {
        this.angelV = angelV;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getStantion() {
        return stantion;
    }

    public void setStantion(String stantion) {
        this.stantion = stantion;
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
