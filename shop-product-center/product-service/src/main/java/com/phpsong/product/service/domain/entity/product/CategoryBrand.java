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
@Table(name = "tb_category_brand")
public class CategoryBrand {
    /**
     * 商品类目id
     */
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 品牌id
     */
    @Id
    @Column(name = "brand_id")
    private Long brandId;


}