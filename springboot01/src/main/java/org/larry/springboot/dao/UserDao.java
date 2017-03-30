package org.larry.springboot.dao;

import com.alibaba.druid.pool.DruidDataSource;
import org.larry.springboot.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Larry on 2017/3/30.
 */
@Repository
public class UserDao {

    private Logger logger = LoggerFactory.getLogger(UserDao.class) ;
    @Autowired
    private JdbcTemplate jdbcTemplate ;

    public List<User> findAll(){
        if(jdbcTemplate.getDataSource() instanceof DruidDataSource){
            logger.info("验证确实通过@Bean注入了DruiDataSource数据源");
        }
        List<User> list = jdbcTemplate.query("select * from user",new BeanPropertyRowMapper(User.class)) ;
        return list ;
    }
}
