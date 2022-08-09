package mg.tife.topo.data.model;

public class Record {
    private Long id;
    private String ltx;
    private String lotissement;
    private String typeTravaux;
    private String adressTerrain;
    private String date;
    private String tn;
    private String parcelle;
    private String proprietaire;
    private Double xo;
    private Double yo;
    private Double zo;
    private Double vo;
    private Long userId;

    public Record() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLtx() {
        return ltx;
    }

    public void setLtx(String ltx) {
        this.ltx = ltx;
    }

    public String getLotissement() {
        return lotissement;
    }

    public void setLotissement(String lotissement) {
        this.lotissement = lotissement;
    }

    public String getTypeTravaux() {
        return typeTravaux;
    }

    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    public String getAdressTerrain() {
        return adressTerrain;
    }

    public void setAdressTerrain(String adressTerrain) {
        this.adressTerrain = adressTerrain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getParcelle() {
        return parcelle;
    }

    public void setParcelle(String parcelle) {
        this.parcelle = parcelle;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getXo() {
        return xo;
    }

    public void setXo(Double xo) {
        this.xo = xo;
    }

    public Double getYo() {
        return yo;
    }

    public void setYo(Double yo) {
        this.yo = yo;
    }

    public Double getZo() {
        return zo;
    }

    public void setZo(Double zo) {
        this.zo = zo;
    }

    public Double getVo() {
        return vo;
    }

    public void setVo(Double vo) {
        this.vo = vo;
    }
}
