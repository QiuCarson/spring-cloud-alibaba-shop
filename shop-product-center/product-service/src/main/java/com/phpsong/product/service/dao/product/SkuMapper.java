package com.phpsong.product.service.dao.product;

import com.phpsong.product.service.domain.entity.product.Sku;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku>,SelectByIdListMapper<Sku,Long> {
}