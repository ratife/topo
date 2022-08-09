package mg.tife.topo.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private Long id;
    private String displayName;
    private String role;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(Long id,String displayName,String role) {
        this.id = id;
        this.displayName = displayName;
        this.role = role;
    }

    String getDisplayName() {
        return displayName;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}