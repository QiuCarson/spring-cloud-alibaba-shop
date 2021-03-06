package com.phpsong.product.service.domain.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "tb_spu")
public class Spu {
    /**
     * spu id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    @Column(name = "sub_title")
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
    @Column(name = "brand_id")
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
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;


}