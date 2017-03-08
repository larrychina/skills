package org.larry.shiro.dao;

import org.junit.Test;
import org.larry.shiro.entity.Permission;
import org.larry.shiro.jdbc.JdbcTemplateUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;

/**
 * Created by Larry on 2017/3/8.
 */
public class PermissionDaoTest {

    private PermissionDao permissionDao = new PermissionDaoImpl() ;
    @Test
    public void createPermission() throws Exception {
        Permission permission = new Permission() ;
        permission.setAvailable(true);
        permission.setPermission("admin:create,admin:update");
        permission = permissionDao.createPermission(permission) ;
        System.out.println(permission.getId());
    }

    @Test
    public void deletePermission() throws Exception {

    }

}