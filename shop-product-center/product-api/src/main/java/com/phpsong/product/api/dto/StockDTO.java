package com.phpsong.product.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class StockDTO {
    /**
     * 库存对应的商品sku id
     */
    private Long skuId;

    /**
     * 可秒杀库存
     */
    private Integer seckillStock;

    /**
     * 秒杀总数量
     */
    private Integer seckillTotal;

    /**
     * 库存数量
     */
    private Integer stock;


}