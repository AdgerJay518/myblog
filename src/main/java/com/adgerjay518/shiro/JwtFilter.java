package com.adgerjay518.shiro;

import com.adgerjay518.common.lang.Result;
import com.adgerjay518.utils.JwtUtils;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴政杰
 */
@Component
public class JwtFilter extends AuthenticatingFilter {
    @Autowired
    JwtUtils jwtUtils;
    /**
     *createToken进入到Realm(com.adgerjay518.shiro.Realm)
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取jwt请求头
        String jwtHeader = request.getHeader("Authorization");
        //如果jwt请求头为空，不交给shiro进行登陆处理
        if (StringUtils.isEmpty(jwtHeader)){
            return null;
        }
        return new JwtToken(jwtHeader);
    }

    /**
     *判断jwt是否过期
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwtHeader = request.getHeader("Authorization");
        // 校验jwt
        if(StringUtils.isEmpty(jwtHeader)) {
            return true;
        } else {
            // 判断是否已过期
            Claims claim = jwtUtils.getClaimByToken(jwtHeader);
            if(claim == null || jwtUtils.isTokenExpired(claim.getExpiration())) {
                throw new ExpiredCredentialsException("token已失效，请重新登录！");
            }
        }
        // 执行登录,会拿到createToken的信息，交给Realm进行处理
        //executeLogin方法是一个封装的方法，需要重写抛出的异常，以便于告诉前端
        return executeLogin(servletRequest, servletResponse);
    }
    @Override
    public boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response){
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result error = Result.error(throwable.getMessage());
            String json = JSONObject.toJSONString(error);
            httpServletResponse.getWriter().print(json);
        }catch (IOException ignored){
        }
        return false;
    }

    /**
     *对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
