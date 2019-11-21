package com.phpsong.product.service.controller;

import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.CartDTO;
import com.phpsong.product.api.dto.SkuDTO;
import com.phpsong.product.api.dto.SpuDTO;
import com.phpsong.product.api.dto.SpuDetailDTO;
import com.phpsong.product.service.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/18
 */
@RestController
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuDTO>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable
    ) {
        return ResponseEntity.ok(goodsService.querySpuByPage(page, rows, key, saleable));
    }

    /**
     * 查询spu详情
     *
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetailDTO> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        return ResponseEntity.ok(goodsService.querySpuDetailBySpuId(spuId));
    }

    /**
     * 根据spuId查询商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<SkuDTO>> querySkuBySpuId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.querySkuBySpuId(id));

    }

    /**
     * 根据sku ids查询sku
     *
     * @param ids
     * @return
     */
    @GetMapping("sku/list/ids")
    public ResponseEntity<List<SkuDTO>> querySkusByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(goodsService.querySkusByIds(ids));
    }


    /**
     * 删除商品
     *
     * @param spuId
     * @return
     */
    @DeleteMapping("spu/spuId/{spuId}")
    public ResponseEntity<Void> deleteGoodsBySpuId(@PathVariable("spuId") Long spuId) {
        goodsService.deleteGoodsBySpuId(spuId);
        return ResponseEntity.ok().build();
    }


    /**
     * 添加商品
     *
     * @param spuDTO
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> addGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.addGoods(spuDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 更新商品
     *
     * @param spuDTO
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.updateGoods(spuDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("spu/saleable")
    public ResponseEntity<Void> handleSaleable(@RequestBody SpuDTO spuDTO) {
        goodsService.handleSaleable(spuDTO);
        return ResponseEntity.ok().build();

    }

    /**
     * 根据spuId查询spu及skus
     *
     * @param spuId
     * @return
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<SpuDTO> querySpuBySpuId(@PathVariable("id") Long spuId) {
        return ResponseEntity.ok(goodsService.querySpuBySpuId(spuId));
    }

    /**
     * 减库存
     *
     * @param cartDtos
     * @return
     */
    @PostMapping("stock/decrease")
    public ResponseEntity<Void> decreaseStock(@RequestBody List<CartDTO> cartDtos) {
        goodsService.decreaseStock(cartDtos);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
