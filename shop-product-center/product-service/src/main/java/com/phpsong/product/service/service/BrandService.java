package com.phpsong.product.service.service;


import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.vo.BrandVo;
import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.BrandDTO;
import com.phpsong.product.service.domain.entity.product.Brand;
import com.phpsong.product.service.domain.entity.product.Category;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/15
 */
public interface BrandService {

    PageResult<BrandDTO> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    void saveBrand(Brand brand, List<Long> cids);

    List<Category> queryCategoryByBid(Long bid);

    void updateBrand(BrandDTO brandVo);

    void deleteBrand(Long bid);

    List<Brand> queryBrandByCid(Long cid);

    Brand queryBrandByBid(Long id);

    List<Brand> queryBrandByIds(List<Long> ids);

}
