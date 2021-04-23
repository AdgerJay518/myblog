package com.adgerjay518.mapper;

import com.adgerjay518.common.dto.LoginDto;
import com.adgerjay518.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    public User findById(Long id);
    public User getUser(LoginDto dto);
}
