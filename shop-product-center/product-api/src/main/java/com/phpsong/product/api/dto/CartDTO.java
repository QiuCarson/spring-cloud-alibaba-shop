package com.phpsong.product.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bystander
 * @date 2018/10/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private Long skuId;  //商品skuId

    private Integer num;  //购买数量
}
