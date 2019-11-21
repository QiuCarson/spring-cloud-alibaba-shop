package com.phpsong.product.service.dao.product;

import com.phpsong.product.service.domain.entity.product.Brand;
import com.phpsong.product.service.domain.entity.product.Category;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand, Long> {

    List<Category> queryCategoryByBid(@Param("bid") Long bid);

    List<Brand> queryBrandByCid(@Param("cid") Long cid);
}