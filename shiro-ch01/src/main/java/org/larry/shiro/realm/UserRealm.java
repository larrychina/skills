package org.larry.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.larry.shiro.dao.UserDao;
import org.larry.shiro.dao.UserDaoImpl;
import org.larry.shiro.entity.User;

/**
 * Created by Larry on 2017/3/8.
 */
public class UserRealm extends AuthorizingRealm {
    private UserDao userDao = new UserDaoImpl() ;
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo() ;
        String userName = (String) principalCollection.getPrimaryPrincipal();
        simpleAuthorizationInfo.setRoles(userDao.findRoles(userName));
        simpleAuthorizationInfo.setStringPermissions(userDao.findPermissions(userName));
        return simpleAuthorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        User user = userDao.findByUserName(userName) ;
        if(user == null)
            throw new UnknownAccountException("账号不存在！") ;
        if(Boolean.TRUE.equals(user.getLocked()))
            throw new LockedAccountException("账号被锁定!") ;
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()),getName()) ;
        return simpleAuthenticationInfo;
    }
}
