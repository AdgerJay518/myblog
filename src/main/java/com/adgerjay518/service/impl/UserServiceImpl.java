package com.adgerjay518.service.impl;

import com.adgerjay518.common.dto.LoginDto;
import com.adgerjay518.mapper.UserMapper;
import com.adgerjay518.pojo.User;
import com.adgerjay518.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User getUser(LoginDto dto) {
        return userMapper.getUser(dto);
    }
}
