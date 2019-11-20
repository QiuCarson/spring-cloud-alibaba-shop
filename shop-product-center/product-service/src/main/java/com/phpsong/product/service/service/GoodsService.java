package com.phpsong.product.service.service;

import cn.hutool.db.PageResult;
import com.leyou.common.vo.PageResult;
import com.leyou.item.dto.CartDto;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.phpsong.product.service.domain.entity.product.Sku;
import com.phpsong.product.service.domain.entity.product.Spu;
import com.phpsong.product.service.domain.entity.product.SpuDetail;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/18
 */
public interface GoodsService {
    PageResult<Spu> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable);

    SpuDetail querySpuDetailBySpuId(Long spuId);

    List<Sku> querySkuBySpuId(Long spuId);

    void deleteGoodsBySpuId(Long spuId);

    void addGoods(Spu spu);

    void updateGoods(Spu spu);

    void handleSaleable(Spu spu);

    Spu querySpuBySpuId(Long spuId);

    List<Sku> querySkusByIds(List<Long> ids);

    void decreaseStock(List<CartDto> cartDtos);
}
