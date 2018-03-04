package rbk.sunrise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rbk.sunrise.base.BaseService;
import rbk.sunrise.dao.RolePermissionMapper;
import rbk.sunrise.entity.RolePermission;

import java.util.List;

@Service
public class RolePermissionService extends BaseService<RolePermission, Long> {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    /**
     * 查找用户的权限
     * @param userId
     * @return
     */
    public List<RolePermission> getRolePermissionsByUserId(Long userId) {
        return rolePermissionMapper.getRolePermissionsByUserId(userId);
    }

}