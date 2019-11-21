package com.phpsong.product.service.dao.product;

import com.phpsong.product.service.domain.entity.product.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category>,IdListMapper<Category,Long> {
}