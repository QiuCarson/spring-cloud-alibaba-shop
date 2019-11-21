package com.phpsong.product.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SpuDTO {
    /**
     * spu id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 1级类目id
     */
    private Long cid1;

    /**
     * 2级类目id
     */
    private Long cid2;

    /**
     * 3级类目id
     */
    private Long cid3;

    /**
     * 商品所属品牌id
     */
    private Long brandId;

    /**
     * 是否上架，0下架，1上架
     */
    private Boolean saleable;

    /**
     * 是否有效，0已删除，1有效
     */
    private Boolean valid;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;

    /**
     * spu所属的分类名称
     */
    private String cname;

    /**
     * spu所属品牌名
     */
    private String bname;

    /**
     * spu详情
     */
    private SpuDetailDTO spuDetail;

    /**
     * sku集合
     */
    private List<SkuDTO> skus;

}