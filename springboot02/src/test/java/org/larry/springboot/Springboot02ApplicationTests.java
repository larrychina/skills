package org.larry.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.larry.springboot.dao.UserDao;
import org.larry.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02ApplicationTests {

	@Autowired
	private UserDao userDao ;

	@Test
	public void contextLoads() {
		List<User> users = userDao.queryAll() ;
		if(users!=null)
			users.forEach(user ->{
				System.out.println(user.toString());
			});
	}

}
