package com.phpsong.product.service.service.impl;

import cn.hutool.core.bean.BeanUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.vo.BrandVo;
import com.leyou.service.mapper.BrandMapper;
import com.leyou.service.service.BrandService;
import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.BrandDTO;
import com.phpsong.product.service.dao.product.BrandMapper;
import com.phpsong.product.service.dao.product.CategoryBrandMapper;
import com.phpsong.product.service.domain.entity.product.Brand;
import com.phpsong.product.service.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

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
        List<BrandDTO> collect = brandList.stream().map(m -> {
            BrandDTO brandDTO = new BrandDTO();
            BeanUtil.copyProperties(m, brandDTO);
            return brandDTO;
        }).collect(Collectors.toList());

        PageInfo<BrandDTO> pageInfo = new PageInfo<>(collect);

        return new PageResult<>(pageInfo.getTotal(), collect);
    }

    @Transactional
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int resultCount = brandMapper.insert(brand);
        if (resultCount == 0) {
            throw new ShopException(ExceptionEnum.BRAND_CREATE_FAILED);
        }
        //更新品牌分类表
        for (Long cid : cids) {
            resultCount = brandMapper.saveCategoryBrand(cid, brand.getId());

            if (resultCount == 0) {
                throw new ShopException(ExceptionEnum.BRAND_CREATE_FAILED);
            }
        }
    }

    @Override
    public List<Category> queryCategoryByBid(Long bid) {
        return brandMapper.queryCategoryByBid(bid);
    }

    @Transactional
    @Override
    public void updateBrand(BrandVo brandVo) {
        Brand brand = new Brand();
        brand.setId(brandVo.getId());
        brand.setName(brandVo.getName());
        brand.setImage(brandVo.getImage());
        brand.setLetter(brandVo.getLetter());

        //更新
        int resultCount = brandMapper.updateByPrimaryKey(brand);
        if (resultCount == 0) {
            throw new LyException(ExceptionEnum.UPDATE_BRAND_FAILED);
        }
        List<Long> cids = brandVo.getCids();
        //更新品牌分类表


        brandMapper.deleteCategoryBrandByBid(brandVo.getId());

        for (Long cid : cids) {
            resultCount = brandMapper.saveCategoryBrand(cid, brandVo.getId());
            if (resultCount == 0) {
                throw new LyException(ExceptionEnum.UPDATE_BRAND_FAILED);
            }

        }


    }

    @Transactional
    @Override
    public void deleteBrand(Long bid) {
        int result = brandMapper.deleteByPrimaryKey(bid);
        if (result == 0) {
            throw new LyException(ExceptionEnum.DELETE_BRAND_EXCEPTION);
        }
        //删除中间表
        result = brandMapper.deleteCategoryBrandByBid(bid);
        if (result == 0) {
            throw new LyException(ExceptionEnum.DELETE_BRAND_EXCEPTION);
        }
    }

    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> brandList = brandMapper.queryBrandByCid(cid);
        if (CollectionUtils.isEmpty(brandList)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brandList;
    }

    @Override
    public Brand queryBrandByBid(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
        Brand b1 = brandMapper.selectByPrimaryKey(brand);
        if (b1 == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return b1;
    }

    @Override
    public List<Brand> queryBrandByIds(List<Long> ids) {
        List<Brand> brands = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }

}
