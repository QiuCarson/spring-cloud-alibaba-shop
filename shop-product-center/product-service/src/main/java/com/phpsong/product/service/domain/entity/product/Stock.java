package com.phpsong.product.service.domain.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "tb_stock")
public class Stock {
    /**
     * 库存对应的商品sku id
     */
    @Id
    @Column(name = "sku_id")
    private Long skuId;

    /**
     * 可秒杀库存
     */
    @Column(name = "seckill_stock")
    private Integer seckillStock;

    /**
     * 秒杀总数量
     */
    @Column(name = "seckill_total")
    private Integer seckillTotal;

    /**
     * 库存数量
     */
    private Integer stock;


}