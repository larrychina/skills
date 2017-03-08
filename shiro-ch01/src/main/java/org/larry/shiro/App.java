package org.larry.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.testng.annotations.Test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    }
    /**
    * Created on 2017/3/7 21:23
     * 用户认证
    */
    @Test
    public void test1(){
        // 1,获取secuityMannager工厂，加载配置文件初始化
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini") ;
        // 2，获得manager实例
        SecurityManager manager = factory.getInstance() ;
        // 将security绑定给securityUtils
        SecurityUtils.setSecurityManager(manager);
        // 3，获取subject,创建token及用户名密码
        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken("lisi","345") ;
        try {
            // 登录
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("are you authenticated? \t:"+subject.isAuthenticated());
        subject.logout();
    }

    // shiro 用户授权
    @org.junit.Test
    public void test2(){
        Subject subject = login("classpath:shiro.ini","lisi","345");
        // 判断是否拥有admin角色
        Boolean flag = subject.hasRole("admin");
        System.out.println(flag); // false
        // 判断是否拥有create权限
        Boolean perimission = subject.isPermitted("find");
        subject.checkPermission("create");
        System.out.println(perimission);
    }

    public Subject login(String filePath,String userName,String password){
        // 1,获取secuityMannager工厂，加载配置文件初始化
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(filePath) ;
        // 2，获得manager实例
        SecurityManager manager = factory.getInstance() ;
        // 将security绑定给securityUtils
        SecurityUtils.setSecurityManager(manager);
        // 3，获取subject,创建token及用户名密码
        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password) ;
        try {
            // 登录
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return subject ;
    }
}
