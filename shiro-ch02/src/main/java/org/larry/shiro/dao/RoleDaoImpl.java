package org.larry.shiro.dao;

import org.larry.shiro.entity.Role;
import org.larry.shiro.jdbc.JdbcTemplateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Larry on 2017/3/8.
 */
public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.getJdbcTemplate() ;

    public Role createRole(final Role role) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder() ;
        final String sql = "insert into sys_role(role,available) values(?,?)" ;
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
                ps.setString(1,role.getRole());
                ps.setBoolean(2,role.getAvailable());
                return ps;
            }
        },generatedKeyHolder) ;
        role.setId(generatedKeyHolder.getKey().longValue());
        return role;
    }

    public void deleteRole(Long id) {
        String sql = "delete from sys_role where id = ?" ;
        jdbcTemplate.update(sql,id) ;
    }

    public void bindRolePermission(Long roleId, Long... permissionIds) {
        if(permissionIds == null || permissionIds.length == 0)
            return ;
        for (Long permissionId:permissionIds){
            jdbcTemplate.update("insert into sys_role_permission(role_id,permission_id) values(?,?)",roleId,permissionId) ;
        }
    }

    public void unBindRolePermission(Long roleId, Long... permissionIds) {
        if(permissionIds == null || permissionIds.length == 0)
            return ;
        for (Long permissionId:permissionIds){
            jdbcTemplate.update("delete from sys_role_permission where role_id=? and permission_id = ?",roleId,permissionId) ;
        }
    }
}
