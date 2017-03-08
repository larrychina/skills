package org.larry.shiro.dao;

import org.larry.shiro.entity.User;
import org.larry.shiro.jdbc.JdbcTemplateUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Larry on 2017/3/8.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.getJdbcTemplate() ;

    public User createUser(final User user) {
        final String sql = "insert into sys_user(user_name,password,salt,locked) values(?,?,?,?)" ;
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"}) ;
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getSalt());
                ps.setBoolean(4, user.getLocked());
                return ps;
            }
        },generatedKeyHolder);
        user.setId(generatedKeyHolder.getKey().longValue());
        return user;
    }

    public void deleteUser(Long id) {
        String sql = "delete from sys_user where id = ?" ;
        jdbcTemplate.update(sql,id);
    }

    public void updateUser(User user) {
        String sql = "update sys_user set user_name=? ,password=?,salt=?,locked=? where id =?" ;
        jdbcTemplate.update(sql,user.getUserName(),user.getPassword(),user.getSalt(),user.getLocked(),user.getId()) ;
    }

    public void bindUserRole(Long userId, Long... roleIds) {
        if(roleIds == null || roleIds.length == 0)
            return ;
        for(Long roleId : roleIds){
            String sql = "insert into sys_user_role(user_id,role_id) values(?,?)" ;
            jdbcTemplate.update(sql,userId,roleId) ;
        }
    }

    public void unbindUserRole(Long userId, Long... roleIds) {
        if(roleIds == null || roleIds.length == 0)
            return ;
        for(Long roleId : roleIds){
            String sql = "delete from sys_user_role where user_id,=? and role_id=?" ;
            jdbcTemplate.update(sql,userId,roleId) ;
        }
    }

    public User findById(Long userId) {
        String sql = "select * from sys_user where id = ?" ;
        List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class),userId);
        if (list!=null && list.size()>0)
            return list.get(0) ;
        return null ;
    }

    public Set<String> findRoles(String userName) {
        String sql = "select r.role from sys_user u ,sys_user_role ur ,sys_role r where u.id = ur.user_id and ur.role_id = r.id and u.user_name = ?" ;
        return new HashSet<String>(jdbcTemplate.queryForList(sql,String.class,userName)) ;
    }

    public Set<String> findPermissions(String userName) {
        String sql = "select p.permission from sys_user u ,sys_user_role ur ,sys_role r,sys_permission p ,sys_role_permission rp where u.id = ur.user_id and ur.role_id = r.id and r.id = rp.role_id and rp.permission_id = p.id and u.user_name = ?" ;
        return new HashSet<String>(jdbcTemplate.queryForList(sql,String.class,userName)) ;
    }

    public User findByUserName(String userName) {
        String sql = "select * from sys_user where user_name=?" ;
        List<User> userList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class),userName) ;
        if (userList == null || userList.isEmpty())
            return null ;
        return userList.get(0) ;
    }

}