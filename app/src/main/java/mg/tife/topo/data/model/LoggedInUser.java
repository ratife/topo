package mg.tife.topo.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private Long userId;
    private String displayName;
    private String role;

    public LoggedInUser(Long userId, String displayName,String role) {
        this.userId = userId;
        this.displayName = displayName;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }
}