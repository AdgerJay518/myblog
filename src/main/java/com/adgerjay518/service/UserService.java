package com.adgerjay518.service;


import com.adgerjay518.common.dto.LoginDto;
import com.adgerjay518.pojo.User;

public interface UserService {
    User findById(Long id);
    User getUser(LoginDto dto);
}
