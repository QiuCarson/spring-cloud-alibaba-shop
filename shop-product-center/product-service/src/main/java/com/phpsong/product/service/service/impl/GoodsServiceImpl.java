package com.phpsong.product.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.common.utils.BeanUtil;
import com.phpsong.common.vo.PageResult;
import com.phpsong.product.api.dto.*;
import com.phpsong.product.service.dao.product.*;
import com.phpsong.product.service.domain.entity.product.*;
import com.phpsong.product.service.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bystander
 * @date 2018/9/18
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;


    @Override
    public PageResult<SpuDTO> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable) {
        //分页
        PageHelper.startPage(page, rows);

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.orEqualTo("saleable", saleable);
        }
        //默认以上一次更新时间排序
        example.setOrderByClause("last_update_time desc");

        //只查询未删除的商品
        criteria.andEqualTo("valid", 1);

        //查询
        List<Spu> spuList = spuMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(spuList)) {
            throw new ShopException(ExceptionEnum.SPU_NOT_FOUND);
        }
        List<SpuDTO> spuDTOList = BeanUtil.copyList(spuList, SpuDTO.class);

        //对查询结果中的分类名和品牌名进行处理
        handleCategoryAndBrand(spuDTOList);

        PageInfo<SpuDTO> pageInfo = new PageInfo<>(spuDTOList);

        return new PageResult<>(pageInfo.getTotal(), spuDTOList);
    }

    @Override
    public SpuDetailDTO querySpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null) {
            throw new ShopException(ExceptionEnum.SPU_NOT_FOUND);
        }
        return BeanUtil.copyProperties(spuDetail, SpuDetailDTO.class);
    }

    @Override
    public List<SkuDTO> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skuList)) {
            throw new ShopException(ExceptionEnum.SKU_NOT_FOUND);
        }
        List<SkuDTO> skuDtoList = BeanUtil.copyList(skuList, SkuDTO.class);
        //查询库存
        for (SkuDTO sku1 : skuDtoList) {
            sku1.setStock(stockMapper.selectByPrimaryKey(sku1.getId()).getStock());
        }
        return skuDtoList;
    }

    @Transactional
    @Override
    public void deleteGoodsBySpuId(Long spuId) {
        if (spuId == null) {
            throw new ShopException(ExceptionEnum.INVALID_PARAM);
        }
        //删除spu,把spu中的valid字段设置成false
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setValid(false);
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count == 0) {
            throw new ShopException(ExceptionEnum.DELETE_GOODS_ERROR);
        }

        //发送消息
        sendMessage(spuId, "delete");
    }

    @Transactional
    @Override
    public void addGoods(SpuDTO spuDTO) {

        Spu spu = BeanUtil.copyProperties(spuDTO, Spu.class);
        //添加商品要添加四个表 spu, spuDetail, sku, stock四张表
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        //插入数据
        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //插入spuDetail数据
        SpuDetailDTO spuDetailDto = spuDTO.getSpuDetail();
        SpuDetail spuDetail = BeanUtil.copyProperties(spuDetailDto, SpuDetail.class);
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insert(spuDetail);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

        //插入sku和库存
        saveSkuAndStock(BeanUtil.copyProperties(spu, SpuDTO.class));

        //发送消息
        sendMessage(spu.getId(), "insert");

    }

    @Transactional
    @Override
    public void updateGoods(SpuDTO spuDTO) {
        if (spuDTO.getId() == 0) {
            throw new ShopException(ExceptionEnum.INVALID_PARAM);
        }
        //首先查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuDTO.getId());
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)) {
            //删除所有sku
            skuMapper.delete(sku);
            //删除库存
            List<Long> ids = skuList.stream()
                    .map(Sku::getId)
                    .collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }

        Spu spu = BeanUtil.copyProperties(spuDTO, Spu.class);
        //更新数据库  spu  spuDetail
        spu.setLastUpdateTime(new Date());
        //更新spu spuDetail
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count == 0) {
            throw new ShopException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }


        SpuDetailDTO spuDetailDTO = spuDTO.getSpuDetail();
        SpuDetail spuDetail = BeanUtil.copyProperties(spuDetailDTO, SpuDetail.class);
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
        if (count == 0) {
            throw new ShopException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }

        //更新sku和stock
        saveSkuAndStock(BeanUtil.copyProperties(spu, SpuDTO.class));

        //发送消息
        sendMessage(spu.getId(), "update");
    }

    @Override
    public void handleSaleable(SpuDTO spuDTO) {
        Spu spu = BeanUtil.copyProperties(spuDTO, Spu.class);
        spu.setSaleable(!spu.getSaleable());
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.UPDATE_SALEABLE_ERROR);
        }
    }

    @Override
    public SpuDTO querySpuBySpuId(Long spuId) {
        //根据spuId查询spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        //查询spuDetail
        SpuDetailDTO detail = querySpuDetailBySpuId(spuId);

        //查询skus
        List<SkuDTO> skus = querySkuBySpuId(spuId);

        SpuDTO spuDTO = BeanUtil.copyProperties(spu, SpuDTO.class);
        spuDTO.setSpuDetail(detail);
        spuDTO.setSkus(skus);

        return spuDTO;

    }

    @Override
    public List<SkuDTO> querySkusByIds(List<Long> ids) {
        List<Sku> skus = skuMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(skus)) {
            throw new ShopException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        List<SkuDTO> skuDTOs = BeanUtil.copyList(skus, SkuDTO.class);
        //填充库存
        fillStock(ids, skuDTOs);

        return skuDTOs;
    }

    @Transactional
    @Override
    public void decreaseStock(List<CartDTO> cartDtos) {
        for (CartDTO cartDto : cartDtos) {
            int count = stockMapper.decreaseStock(cartDto.getSkuId(), cartDto.getNum());
            if (count != 1) {
                throw new ShopException(ExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }
    }

    private void fillStock(List<Long> ids, List<SkuDTO> skus) {
        //批量查询库存
        List<Stock> stocks = stockMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(stocks)) {
            throw new ShopException(ExceptionEnum.STOCK_NOT_FOUND);
        }
        //首先将库存转换为map，key为sku的ID
        Map<Long, Integer> map = stocks.stream().collect(Collectors.toMap(s -> s.getSkuId(), s -> s.getStock()));

        //遍历skus，并填充库存
        for (SkuDTO sku : skus) {
            sku.setStock(map.get(sku.getId()));
        }
    }


    /**
     * 保存sku和库存
     *
     * @param spuDTO
     */
    private void saveSkuAndStock(SpuDTO spuDTO) {

        List<SkuDTO> skuList = spuDTO.getSkus();


        List<StockDTO> stocks = new ArrayList<>();

        for (SkuDTO skuDTO : skuList) {
            skuDTO.setSpuId(spuDTO.getId());
            skuDTO.setCreateTime(new Date());
            skuDTO.setLastUpdateTime(spuDTO.getCreateTime());

            Sku sku = BeanUtil.copyProperties(skuDTO, Sku.class);
            int count = skuMapper.insert(sku);
            if (count != 1) {
                throw new ShopException(ExceptionEnum.GOODS_SAVE_ERROR);
            }

            StockDTO stockDTO = new StockDTO();
            stockDTO.setSkuId(sku.getId());
            stockDTO.setStock(skuDTO.getStock());
            stocks.add(stockDTO);
        }
        //批量插入库存数据
        int count = stockMapper.insertList(BeanUtil.copyList(stocks, Stock.class));
        if (count == 0) {
            throw new ShopException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }


    /**
     * 处理商品分类名和品牌名
     *
     * @param spuList
     */
    private void handleCategoryAndBrand(List<SpuDTO> spuList) {
        for (SpuDTO spu : spuList) {
            //根据spu中的分类ids查询分类名
            List<String> nameList = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            //对分类名进行处理
            spu.setCname(StringUtils.join(nameList, "/"));

            //查询品牌
            spu.setBname(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
        }
    }

    /**
     * 封装发送到消息队列的方法
     *
     * @param id
     * @param type
     */
    private void sendMessage(Long id, String type) {
        try {
            //TODO rocketMQ发送消息
            //amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            log.error("{}商品消息发送异常，商品ID：{}", type, id, e);
        }
    }
}
