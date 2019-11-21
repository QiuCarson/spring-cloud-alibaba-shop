package com.phpsong.product.service.service;
import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.BrandDTO;
import com.phpsong.product.api.dto.CategoryDTO;
import com.phpsong.product.api.dto.UpdateBrandDTO;
import com.phpsong.product.service.domain.entity.product.Brand;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/15
 */
public interface BrandService {

    PageResult<BrandDTO> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    void saveBrand(BrandDTO brandDTO, List<Long> cids);

    List<CategoryDTO> queryCategoryByBid(Long bid);

    void updateBrand(UpdateBrandDTO brandVo);

    void deleteBrand(Long bid);

    List<BrandDTO> queryBrandByCid(Long cid);

    BrandDTO queryBrandByBid(Long id);

    List<BrandDTO> queryBrandByIds(List<Long> ids);

}
