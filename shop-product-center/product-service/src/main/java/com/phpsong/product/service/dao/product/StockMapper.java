package com.phpsong.product.service.dao.product;

import com.phpsong.product.service.domain.entity.product.Stock;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends Mapper<Stock>, DeleteByIdListMapper<Stock, Long>, SelectByIdListMapper<Stock, Long>,InsertListMapper<Stock> {

    int decreaseStock(@Param("skuId") Long skuId, @Param("num") Integer num);
}