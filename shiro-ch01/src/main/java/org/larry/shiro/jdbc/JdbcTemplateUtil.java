package org.larry.shiro.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Larry on 2017/3/8.
 * 使用spring jdbc访问数据库
 */
public class JdbcTemplateUtil {

    private static JdbcTemplate jdbcTemplate ;

    // 考虑线程安全，但是效率相对较低
    public static synchronized JdbcTemplate getJdbcTemplate(){
        if(jdbcTemplate == null)
            jdbcTemplate = createJdbcTemplate() ;
        return jdbcTemplate ;
    }

    private static JdbcTemplate createJdbcTemplate() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/shiro_au?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return new JdbcTemplate(dataSource) ;
    }
}
