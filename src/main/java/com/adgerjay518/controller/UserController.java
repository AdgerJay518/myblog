package com.adgerjay518.controller;


import cn.hutool.core.map.MapUtil;
import com.adgerjay518.common.dto.LoginDto;
import com.adgerjay518.common.lang.Result;
import com.adgerjay518.pojo.User;
import com.adgerjay518.service.UserService;
import com.adgerjay518.utils.JwtUtils;
import com.adgerjay518.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    //@RequiresAuthentication
    @GetMapping("/findById")
    public Result findById(){
        User byId = userService.findById(1L);
        return Result.success(byId);
    }

    @PostMapping("/mit")
    public Result mit(@Validated @RequestBody User user){
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getUser(loginDto);
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(MD5Utils.getMD5String(loginDto.getPassword()))){
            return Result.error("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        /*return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );*/
        Map<Object,Object> mapUtil=new HashMap<>();
        mapUtil.put("id", user.getId());
        mapUtil.put("username", user.getUsername());
        mapUtil.put("avatar", user.getAvatar());
        mapUtil.put("email", user.getEmail());
        //return Result.success(user);
        return Result.success(mapUtil);
    }
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

    @PostMapping("/userName")
    public Result userName(@Validated @RequestBody LoginDto dto){
        User user = userService.getUser(dto);
        return Result.success(user);
    }
}
