package mg.tife.topo.data.model;

public class User {
    private Long id;
    private String login;
    private String nom;
    private String prenom;
    private String role;

    public User(Long id, String login, String nom, String prenom, String role) {
        this.id = id;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPrintName() {
        return nom + "  " +prenom + "("+login+")";
    }
}
