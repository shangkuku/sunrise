package rbk.sunrise.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import rbk.sunrise.entity.RolePermission;
import rbk.sunrise.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtUser工厂类
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user, List<RolePermission> permissions) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(permissions),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RolePermission> permissions) {
        return permissions.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toList());
    }
}
