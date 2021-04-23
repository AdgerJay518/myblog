package com.adgerjay518.shiro;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * shiro默认supports的是UsernamePasswordToken，而我们现在采用了jwt的方式，
 * 所以这里我们自定义一个JwtToken，来完成shiro的supports方法
 * @author 吴政杰
 */
public class JwtToken implements AuthenticationToken {

    private final String token;
    public JwtToken(String jwt){
        this.token=jwt;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
