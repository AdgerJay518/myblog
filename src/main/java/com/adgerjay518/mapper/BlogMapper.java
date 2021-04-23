package com.adgerjay518.mapper;

import com.adgerjay518.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {
    /**
     * 得到当前页面博客数据
     * @return 博客相关内容
     */
    public List<Blog> getPageData();

    /**
     *根据id查找博客
     * @param id 博客号
     * @return 博客相关内容
     */
    public Blog getById(Long id);

    /**
     * 对博客进行编辑
     * @param blog 博客相关内容
     */
    public void edit(Blog blog);
}
