package rbk.sunrise.service;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rbk.sunrise.base.BaseService;
import rbk.sunrise.entity.User;

@Service
public class UserService extends BaseService<User, Long> {

    @Autowired
    PasswordService defaultPasswordService;

    @Override
    public int insert(User user) {
        user.setPassword(defaultPasswordService.encryptPassword(user.getPassword()));
        return super.baseMapper.insert(user);
    }



}