package com.adgerjay518.controller;

import cn.hutool.core.bean.BeanUtil;
import com.adgerjay518.common.lang.Result;
import com.adgerjay518.pojo.Blog;
import com.adgerjay518.service.BlogService;
import com.adgerjay518.utils.ShiroUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    public Result pageData(@RequestParam(defaultValue = "1") Integer currentPage){
        //设置分页相关参数 当前页+每页显示的条数
        PageHelper.startPage(currentPage, 5);
        List<Blog> pageData=blogService.getPageData();
        PageInfo<Blog> userPageInfo = new PageInfo<Blog>(pageData);
        //System.out.println("currentPage:"+userPageInfo.getPageNum());
        return Result.success(userPageInfo);
    }

    @GetMapping("/blog/{id}")
    public Result getById(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客不存在或已被删除");
        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp=null;
        //如果当前用户存在，可以进行编辑，但只能编辑自己的文章
        if (blog.getId()!=null){
            temp=blogService.getById(blog.getId());
            System.out.println(ShiroUtil.getProfile().getId());
            System.out.println(blog.getId());
            System.out.println(temp);
            //对是否是当前用户的id进行断言
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");
        }
        else {//添加
            temp=new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.edit(temp);
        return Result.success(null);
    }

}
