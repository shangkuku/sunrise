package rbk.sunrise.shiro;


import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rbk.sunrise.entity.Role;
import rbk.sunrise.entity.RolePermission;
import rbk.sunrise.entity.User;
import rbk.sunrise.service.RolePermissionService;
import rbk.sunrise.service.RoleService;
import rbk.sunrise.service.UserService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) {
            throw new AuthorizationException("没有principals");
        }

        String principal = (String) getAvailablePrincipal(principals);

        User u = getUserByPrincipal(principal);
        List<Role> roles = roleService.getRolesByUserId(u.getId());
        Set<String> roleNames = roles.stream().map(Role::getCode).collect(Collectors.toSet());
        Set<String> permissions = getPermissions(u.getId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    private Set<String> getPermissions(Long userId) {
        List<RolePermission> rolePermissionList = rolePermissionService.getRolePermissionsByUserId(userId);
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return Collections.EMPTY_SET;
        }
        // todo
        return null;
//        return new HashSet(rolePermissionList.stream().map);
    }


    /**
     * 通过用户标识查找角色。留待拓展，principal可以为其他唯一字段，如手机号、邮箱
     *
     * @param principal
     * @return
     */
    private User getUserByPrincipal(String principal) {
        return userService.selectOne(User.builder().name(principal).build());
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        if (username == null) {
            throw new AccountException("用户名不能为null");
        }

        SimpleAuthenticationInfo info;

        User u = getUserByName(username);
        String password = u.getPassword();
        String salt = u.getSalt();

        if (password == null) {
            throw new UnknownAccountException("[" + username + "]没有密码");
        }
        info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

        if (salt != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        }
        return info;
    }

    private User getUserByName(String username) {
        return userService.selectOne(User.builder().name(username).build());
    }
}
