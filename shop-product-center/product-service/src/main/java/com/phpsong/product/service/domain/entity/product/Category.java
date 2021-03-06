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
@Table(name = "tb_category")
public class Category {
    /**
     * 类目id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 父类目id,顶级类目填0
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 是否为父节点，0为否，1为是
     */
    @Column(name = "is_parent")
    private Boolean isParent;

    /**
     * 排序指数，越小越靠前
     */
    private Integer sort;
}