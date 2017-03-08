package org.larry.shiro.dao;

import org.junit.Test;
import org.larry.shiro.entity.Role;

import static org.junit.Assert.*;

/**
 * Created by Larry on 2017/3/8.
 */
public class RoleDaoTest {
    private RoleDao roleDao = new RoleDaoImpl() ;

    @Test
    public void createRole() throws Exception {
        Role role = new Role() ;
        role.setRole("管理员");
        role.setAvailable(true);
        role = roleDao.createRole(role) ;
        System.out.println(role.getId());
    }

    @Test
    public void deleteRole() throws Exception {

    }

    @Test
    public void bindRolePermission() throws Exception {
        roleDao.bindRolePermission(1l,1l);
    }

    @Test
    public void unBindRolePermission() throws Exception {

    }

}