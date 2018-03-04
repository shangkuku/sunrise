package rbk.sunrise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbk.sunrise.base.BaseService;
import rbk.sunrise.entity.User;

import java.util.Date;

@Service
@Transactional
public class UserService extends BaseService<User, Long> {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public int insert(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastPasswordResetDate(new Date());
        user.setCreateTime(new Date());
        return super.baseMapper.insert(user);
    }

}