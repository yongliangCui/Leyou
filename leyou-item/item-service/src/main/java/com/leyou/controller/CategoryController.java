package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.junit.runners.Parameterized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("list")
    @ResponseBody
    public ResponseEntity<List<Category>> findById(@RequestParam(value = "pid",defaultValue = "0") Long pid){
        if (pid == null || pid < 0){
            return ResponseEntity.badRequest().build();
        }
        List<Category> list = categoryService.findByPid(pid);
        if (CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

}
