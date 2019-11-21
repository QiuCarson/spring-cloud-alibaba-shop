package com.phpsong.product.service.service;


import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.CartDTO;
import com.phpsong.product.api.dto.SkuDTO;
import com.phpsong.product.api.dto.SpuDTO;
import com.phpsong.product.api.dto.SpuDetailDTO;
import com.phpsong.product.service.domain.entity.product.Spu;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/18
 */
public interface GoodsService {
    PageResult<SpuDTO> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable);

    SpuDetailDTO querySpuDetailBySpuId(Long spuId);

    List<SkuDTO> querySkuBySpuId(Long spuId);

    void deleteGoodsBySpuId(Long spuId);

    void addGoods(SpuDTO spu);

    void updateGoods(SpuDTO spu);

    void handleSaleable(SpuDTO spu);

    SpuDTO querySpuBySpuId(Long spuId);

    List<SkuDTO> querySkusByIds(List<Long> ids);

    void decreaseStock(List<CartDTO> cartDtos);
}
