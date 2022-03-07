package mg.tife.topo.data.model;

public class Record {
    private Long id;
    private String ltx;
    private String lotissement;
    private Long userId;

    public String getLtx() {
        return ltx;
    }

    public String getLotissement() {
        return lotissement;
    }

    public Long getId() {
        return id;
    }

    public Record(Long id, String ltx, String lotissement) {
        this.id = id;
        this.ltx = ltx;
        this.lotissement = lotissement;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
