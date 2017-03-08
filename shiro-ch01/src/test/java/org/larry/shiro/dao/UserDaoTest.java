package org.larry.shiro.dao;

import org.junit.Test;
import org.larry.shiro.entity.User;

import static org.junit.Assert.*;

/**
 * Created by Larry on 2017/3/8.
 */
public class UserDaoTest {
    private UserDao userDao = new UserDaoImpl() ;

    @Test
    public void createUser() throws Exception {
        User user = new User() ;
        user.setLocked(true);
        user.setUserName("zhangsan");
        user.setPassword("LKNG:LS*#W0w43");
        user.setSalt("12311");
        user = userDao.createUser(user) ;
        System.out.println(user.getId());
    }

    @Test
    public void deleteUser() throws Exception {
        Long id = 1L ;
        userDao.deleteUser(id);
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User() ;
        user.setLocked(false);
        user.setUserName("zhangsan");
        user.setPassword("1234");
        user.setId(2L);
        user.setSalt("12311");
        userDao.updateUser(user); ;

    }

    @Test
    public void bindUserRole() throws Exception {
        userDao.bindUserRole(2l,1l);
    }

    @Test
    public void unbindUserRole() throws Exception {

    }

    @Test
    public void findById() throws Exception {
        long id = 1L ;
        User user = userDao.findById(id) ;
        System.out.println(user);
    }

    @Test
    public void findRoles() throws Exception {
        System.out.println(userDao.findRoles("zhangsan")) ;
    }

    @Test
    public void findPermissions() throws Exception {
        System.out.println(userDao.findPermissions("zhangsan")) ;
    }

}