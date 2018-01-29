package rbk.sunrise.dao;

import org.apache.ibatis.annotations.Param;
import rbk.sunrise.base.BaseMapper;
import rbk.sunrise.entity.Role;
import rbk.sunrise.entity.User;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByUser(@Param("u") User u);

}