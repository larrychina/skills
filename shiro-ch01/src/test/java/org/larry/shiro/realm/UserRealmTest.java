package org.larry.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * Created by Larry on 2017/3/8.
 */
public class UserRealmTest {
    public Subject login(String filePath,String userName,String password){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(filePath) ;
        SecurityManager securityManager = factory.getInstance() ;
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password) ;
        try {
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return subject ;
    }

    @Test
    public void testUserRealm(){
       Subject subject = login("classpath:shiro.ini","zhangsan","1234") ;
        System.out.println(subject.isAuthenticated());
        System.out.println(subject.hasRole("管理员"));
        System.out.println(subject.isPermitted("admin:create,admin:update"));
    }
}
