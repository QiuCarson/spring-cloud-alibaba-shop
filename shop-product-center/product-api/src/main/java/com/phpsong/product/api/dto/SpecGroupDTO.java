package com.phpsong.product.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SpecGroupDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商品分类id，一个分类下有多个规格组
     */
    private Long cid;

    /**
     * 规格组的名称
     */
    private String name;

    private List<SpecParamDTO> params;

}