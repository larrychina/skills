package org.larry.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.larry.springboot.entity.User;

import java.util.List;

/**
 * Created by Larry on 2017/3/31.
 */
@Mapper
public interface UserDao {

    @Select("select id,name from user where id=#{id}")
    User findById(@Param("id") int id) ;

    // @Select("select * from user")
    List<User> queryAll() ;
}
