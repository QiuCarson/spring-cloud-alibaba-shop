package com.phpsong.product.service.service.impl;

import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.common.utils.BeanUtil;
import com.phpsong.product.api.dto.CategoryDTO;
import com.phpsong.product.service.dao.product.CategoryMapper;
import com.phpsong.product.service.domain.entity.product.Category;
import com.phpsong.product.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author bystander
 * @date 2018/9/15
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<CategoryDTO> queryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(categoryList)) {
            throw new ShopException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return BeanUtil.copyList(categoryList, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> queryCategoryByIds(List<Long> ids) {
        List<Category> categorys = categoryMapper.selectByIdList(ids);
        return BeanUtil.copyList(categorys, CategoryDTO.class);

    }

    @Override
    public List<CategoryDTO> queryAllByCid3(Long id) {
        Category c3 = categoryMapper.selectByPrimaryKey(id);
        Category c2 = categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = categoryMapper.selectByPrimaryKey(c2.getParentId());
        List<Category> list = Arrays.asList(c1, c2, c3);
        if (CollectionUtils.isEmpty(list)) {
            throw new ShopException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return BeanUtil.copyList(list, CategoryDTO.class);
    }
}
