package org.larry.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.larry.shiro.entity.User;
import org.larry.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Larry on 2017/3/9.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService ;

    // 授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo() ;
        simpleAuthorizationInfo.setStringPermissions(userService.findPermissions(userName));
        simpleAuthorizationInfo.setRoles(userService.findRoles(userName));
        return simpleAuthorizationInfo;
    }

    // 认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        User user = userService.findByUsername(userName) ;
        if(user == null)
            throw new UnknownAccountException("用户不存在！") ;
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),ByteSource.Util.bytes(user.getCredentialsSalt()),getName()) ;
        return simpleAuthenticationInfo;
    }
}
