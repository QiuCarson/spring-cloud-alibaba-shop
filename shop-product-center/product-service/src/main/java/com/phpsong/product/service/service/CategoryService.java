package com.phpsong.product.service.service;

import com.phpsong.product.api.dto.CategoryDTO;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/15
 */
public interface CategoryService {


    List<CategoryDTO> queryCategoryByPid(Long pid);

    List<CategoryDTO> queryCategoryByIds(List<Long> ids);

    List<CategoryDTO> queryAllByCid3(Long id);
}
