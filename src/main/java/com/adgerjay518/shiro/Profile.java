package com.adgerjay518.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴政杰
 */
@Data
public class Profile implements Serializable {
    private Long id;
    private String username;
}
