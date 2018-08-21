package org.larrychina.springboot.springboot07.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SpUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate ;


    public List<Map<String, Object>> queryAll() {
        return jdbcTemplate.queryForList("select id,login_name as loginName,user_pwd as userPwd,mobile as mobile from sp_user");
    }
}
