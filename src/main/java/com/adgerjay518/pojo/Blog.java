package com.adgerjay518.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)//不使用equal和hashcode方法
@Accessors(chain = true)
public class Blog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "摘要不能为空")
    private String description;
    @NotBlank(message = "内容不能为空")
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime created;
    private Integer status;
}
