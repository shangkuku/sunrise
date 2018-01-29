package rbk.sunrise.shiro;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rbk.sunrise.entity.User;
import rbk.sunrise.service.UserService;
import tk.mybatis.mapper.entity.Example;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MyRealm extends AuthorizingRealm {


    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        SimpleAuthenticationInfo info;

        String password;
        String salt;
        String[] queryResults = getPasswordForUser(username);
        password = queryResults[0];
        salt = queryResults[1];


        if (password == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

        if (salt != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        }
        return info;
    }

    private String[] getPasswordForUser(String username) {

    }
}
