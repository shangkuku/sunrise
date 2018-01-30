package rbk.sunrise.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

    @RequestMapping("/login")
    public ResponseEntity login() {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession();
        if (subject.isAuthenticated()) {
            throw new AuthenticationException("用户已登录");
        }
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456", true);
        subject.login(token);
        return ResponseEntity.ok("登录成功");
    }


//    @RequestMapping("/logout")
//    public ResponseEntity logout() {
//
//    }
}
