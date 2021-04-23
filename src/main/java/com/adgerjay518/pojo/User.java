package com.adgerjay518.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)//不使用equal和hashcode方法
@Accessors(chain = true)

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String avatar;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    private String password;
    private Integer status;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
}
