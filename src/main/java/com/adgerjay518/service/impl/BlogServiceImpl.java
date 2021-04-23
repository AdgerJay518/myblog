package com.adgerjay518.service.impl;


import com.adgerjay518.mapper.BlogMapper;
import com.adgerjay518.pojo.Blog;
import com.adgerjay518.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "BlogService")
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> getPageData() {
        return blogMapper.getPageData();
    }

    @Override
    public Blog getById(Long id) {
        return blogMapper.getById(id);
    }

    @Override
    public void edit(Blog blog){
        blogMapper.edit(blog);
    }
}
