package rbk.sunrise.dao;

import org.apache.ibatis.annotations.Param;
import rbk.sunrise.base.BaseMapper;
import rbk.sunrise.entity.Role;
import rbk.sunrise.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<RolePermission> getRolePermissionsByUserId(@Param("userId") Long userId);
}