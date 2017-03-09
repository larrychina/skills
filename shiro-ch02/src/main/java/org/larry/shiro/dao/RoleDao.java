package org.larry.shiro.dao;

import org.larry.shiro.entity.Role;

/**
 * Created by Larry on 2017/3/8.
 */
public interface RoleDao {

    // 创建角色
    public Role createRole(Role role) ;

    // 删除角色
    void deleteRole(Long id) ;

    // 角色授权
    void bindRolePermission(Long roleId, Long... permissionIds) ;

    // 解除角色授权
    void unBindRolePermission(Long roleId, Long... permissionIds) ;

}
