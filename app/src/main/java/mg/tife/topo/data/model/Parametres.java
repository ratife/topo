package mg.tife.topo.data.model;

public class Parametres {
    private String path;
    private String mode;

    public Parametres(String path, String mode) {
        this.path = path;
        this.mode = mode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
