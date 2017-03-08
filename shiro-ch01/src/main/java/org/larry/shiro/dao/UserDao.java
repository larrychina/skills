package org.larry.shiro.dao;

import org.larry.shiro.entity.User;

import java.util.Set;

/**
 * Created by Larry on 2017/3/8.
 */
public interface UserDao {

    User createUser(User user) ;

    void deleteUser(Long id) ;

    void updateUser(User user) ;

    // 添加绑定关系
    void bindUserRole(Long userId,Long...roleIds) ;

    // 接触绑定关系
    void unbindUserRole(Long userId,Long... roleIds) ;

    User findById(Long userId);

    // 获取用户角色
    Set<String> findRoles(String userName) ;

    //获取用户权限
    Set<String> findPermissions(String userName) ;

    User findByUserName(String userName) ;

}
