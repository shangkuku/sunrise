package rbk.sunrise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rbk.sunrise.dao.RolePermissionMapper;
import rbk.sunrise.dao.UserMapper;
import rbk.sunrise.entity.RolePermission;
import rbk.sunrise.entity.User;

import java.util.List;


/**
 * UserDetailsService实现类。怎么来通过Username获取User对象
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(User.builder().name(username).build());

        List<RolePermission> permissionList = rolePermissionMapper.getRolePermissionsByUserId(user.getId());


        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user, permissionList);
        }
    }
}
