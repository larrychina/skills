package org.larry.shiro.dao;

import org.larry.shiro.entity.Permission;
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
public class PermissionDaoImpl implements PermissionDao {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.getJdbcTemplate() ;

    public Permission createPermission(final Permission permission) {
        final String sql = "insert into sys_permission(permission,available) values(?,?)" ;
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder() ;
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"}) ;
                ps.setString(1,permission.getPermission());
                ps.setBoolean(2,permission.getAvailable());
                return ps ;
            }
        },generatedKeyHolder);
        permission.setId(generatedKeyHolder.getKey().longValue());
        return permission;
    }

    public void deletePermission(Long id) {
        String sql = "delete from sys_permission where id = ? " ;
        jdbcTemplate.update(sql,id) ;
    }
}
