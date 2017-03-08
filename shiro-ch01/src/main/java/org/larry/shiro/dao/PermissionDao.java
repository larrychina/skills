package org.larry.shiro.dao;

import org.larry.shiro.entity.Permission;

/**
 * Created by Larry on 2017/3/8.
 */
public interface PermissionDao {

    Permission createPermission(Permission permission) ;

    void deletePermission(Long id) ;
}
