package app.configuration;

public enum UserPermission {
    WRITE("WRITE"), READ("READ"), EDIT("READ"), DELETE("DELETE");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
