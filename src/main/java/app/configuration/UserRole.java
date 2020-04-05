package app.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static app.configuration.UserPermission.*;

public enum UserRole implements GrantedAuthority{

    ROLE_ADMIN(new HashSet<>(Arrays.asList(WRITE, READ, EDIT, DELETE))),
    ROLE_MODERATOR(new HashSet<>(Arrays.asList(WRITE, READ, EDIT))),
    ROLE_USER(new HashSet<>(Arrays.asList(WRITE, READ)));

    private final Set<UserPermission> permisions;

    UserRole(Set<UserPermission> permisions) {
        this.permisions = permisions;
    }

    public Set<UserPermission> getPermisions() {
        return permisions;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
