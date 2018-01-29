package rbk.sunrise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rbk.sunrise.base.BaseService;
import rbk.sunrise.dao.RoleMapper;
import rbk.sunrise.entity.Role;
import rbk.sunrise.entity.User;

import java.util.List;

@Service
public class RoleService extends BaseService<Role, Long> {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 通过用户id查找拥有的角色
     * @param userId
     * @return
     */
    public List<Role> getRolesByUserId(Long userId) {
        User u = User.builder().id(userId).build();
        return roleMapper.getRolesByUser(u);
    }

}