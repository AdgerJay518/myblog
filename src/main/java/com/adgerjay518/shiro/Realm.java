package com.adgerjay518.shiro;

import com.adgerjay518.pojo.User;
import com.adgerjay518.service.UserService;
import com.adgerjay518.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Component;

/**
 * @author 吴政杰
 */
@Component
public class Realm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     *判断token是否是JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //封装jwt
        JwtToken jwtToken=(JwtToken) authenticationToken;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.findById(Long.valueOf(userId));
        if (user==null){
            throw new UnknownAccountException("账户不存在");
        }
        if (user.getStatus()==-1){
            throw new LockedAccountException("账户已被锁定");
        }
        Profile profile = new Profile();
        BeanUtil.copyProperties(user,profile);
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
