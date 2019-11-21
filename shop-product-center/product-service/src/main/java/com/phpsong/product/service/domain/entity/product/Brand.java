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
@Table(name = "tb_brand")
public class Brand {
    /**
     * 品牌id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌图片地址
     */
    private String image;

    /**
     * 品牌的首字母
     */
    private String letter;

}