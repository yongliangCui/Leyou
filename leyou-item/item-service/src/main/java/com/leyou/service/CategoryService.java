package com.leyou.service;

import com.leyou.mapper.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("CategoryService")
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public List<Category> findAll(){
        List<Category> categories = categoryMapper.selectAll();
        return categories;
    }

    public List<Category> findByPid(Long pid){
        Category c = new Category();
        c.setParent_id(pid);
        return categoryMapper.select(c);
    }


}
