package com.leyou.service;

import com.common.pojo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.mapper.BrandMapper;
import com.leyou.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service("BrandService")
public class BrandService {
    @Resource
    private BrandMapper brandMapper;

    /**
     * 分页查询，使用PageHelper中间件进行分页。
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandPages(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
//      是否模糊查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

//      排序方法和如何排序
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc ? "desc":"asc"));
        }
        //      查询的页数和行数
        PageHelper.startPage(page,rows);
//      执行查询
        List<Brand> brands = brandMapper.selectByExample(example);
//      将执行结果放入分页
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        PageResult<Brand> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

    /**
     * 创建新品牌，两张表
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
//    新增brand表
        brandMapper.insertSelective(brand);
//    新增中间表
        for (Long cid : cids) {
            brandMapper.insertBrandAndCategory(cid, brand.getId());
        }
    }
}