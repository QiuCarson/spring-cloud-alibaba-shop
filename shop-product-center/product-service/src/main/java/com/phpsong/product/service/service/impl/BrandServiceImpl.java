package com.phpsong.product.service.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.common.utils.BeanUtil;
import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.BrandDTO;
import com.phpsong.product.api.dto.CategoryDTO;
import com.phpsong.product.api.dto.UpdateBrandDTO;
import com.phpsong.product.service.dao.product.BrandMapper;
import com.phpsong.product.service.dao.product.CategoryBrandMapper;
import com.phpsong.product.service.domain.entity.product.Brand;
import com.phpsong.product.service.domain.entity.product.Category;
import com.phpsong.product.service.domain.entity.product.CategoryBrand;
import com.phpsong.product.service.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/15
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;


    @Override
    public PageResult<BrandDTO> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //开启分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            String sortByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(sortByClause);
        }
        List<Brand> brandList = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(brandList)) {
            throw new ShopException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        // 转换成DTO对象
        List<BrandDTO> brandDTOS = BeanUtil.copyList(brandList, BrandDTO.class);


        PageInfo<BrandDTO> pageInfo = new PageInfo<>(brandDTOS);

        return new PageResult<>(pageInfo.getTotal(), brandDTOS);
    }

    @Transactional
    @Override
    public void saveBrand(BrandDTO brandDTO, List<Long> cids) {
        Brand brand=BeanUtil.copyProperties(brandDTO,Brand.class);
        brand.setId(null);
        int resultCount = brandMapper.insert(brand);
        if (resultCount == 0) {
            throw new ShopException(ExceptionEnum.BRAND_CREATE_FAILED);
        }
        //更新品牌分类表
        for (Long cid : cids) {

            CategoryBrand categoryBrand = CategoryBrand.builder()
                    .brandId(brand.getId())
                    .categoryId(cid)
                    .build();
            resultCount = categoryBrandMapper.insert(categoryBrand);

            if (resultCount == 0) {
                throw new ShopException(ExceptionEnum.BRAND_CREATE_FAILED);
            }
        }
    }

    @Override
    public List<CategoryDTO> queryCategoryByBid(Long bid) {
        List<Category> categories = brandMapper.queryCategoryByBid(bid);
        // 对象转换
        List<CategoryDTO> categoryDTOS = BeanUtil.copyList(categories, CategoryDTO.class);
        return categoryDTOS;
    }

    @Transactional
    @Override
    public void updateBrand(UpdateBrandDTO brandVo) {
        Brand brand = new Brand();
        brand.setId(brandVo.getId());
        brand.setName(brandVo.getName());
        brand.setImage(brandVo.getImage());
        brand.setLetter(brandVo.getLetter());

        //更新
        int resultCount = brandMapper.updateByPrimaryKey(brand);
        if (resultCount == 0) {
            throw new ShopException(ExceptionEnum.UPDATE_BRAND_FAILED);
        }
        List<Long> cids = brandVo.getCids();
        //更新品牌分类表

        categoryBrandMapper.delete(CategoryBrand.builder().brandId(brandVo.getId()).build());

        for (Long cid : cids) {
            resultCount = categoryBrandMapper.insert(CategoryBrand.builder()
                    .brandId(brandVo.getId())
                    .categoryId(cid)
                    .build());
            if (resultCount == 0) {
                throw new ShopException(ExceptionEnum.UPDATE_BRAND_FAILED);
            }

        }


    }

    @Transactional
    @Override
    public void deleteBrand(Long bid) {
        int result = brandMapper.deleteByPrimaryKey(bid);
        if (result == 0) {
            throw new ShopException(ExceptionEnum.DELETE_BRAND_EXCEPTION);
        }
        //删除中间表
        result = categoryBrandMapper.delete(CategoryBrand.builder().brandId(bid).build());
        if (result == 0) {
            throw new ShopException(ExceptionEnum.DELETE_BRAND_EXCEPTION);
        }
    }

    @Override
    public List<BrandDTO> queryBrandByCid(Long cid) {
        List<Brand> brandList = brandMapper.queryBrandByCid(cid);
        if (CollectionUtils.isEmpty(brandList)) {
            throw new ShopException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return BeanUtil.copyList(brandList,BrandDTO.class);
    }

    @Override
    public BrandDTO queryBrandByBid(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
        Brand b1 = brandMapper.selectByPrimaryKey(brand);
        if (b1 == null) {
            throw new ShopException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return BeanUtil.copyProperties(b1,BrandDTO.class);
    }

    @Override
    public List<BrandDTO> queryBrandByIds(List<Long> ids) {
        List<Brand> brands = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(brands)) {
            throw new ShopException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return BeanUtil.copyList(brands,BrandDTO.class);
    }

}
