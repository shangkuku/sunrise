package rbk.sunrise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rbk.sunrise.dao.RoleMapper;
import rbk.sunrise.dao.UserMapper;
import rbk.sunrise.entity.Role;
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
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(User.builder().name(username).build());

        List<Role> roleList = roleMapper.getRolesByUser(user);


        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user, roleList);
        }
    }
}
