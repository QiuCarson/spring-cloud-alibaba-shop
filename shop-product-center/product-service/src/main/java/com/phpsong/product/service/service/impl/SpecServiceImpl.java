package com.phpsong.product.service.service.impl;

import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.common.utils.BeanUtil;
import com.phpsong.product.api.dto.SpecGroupDTO;
import com.phpsong.product.api.dto.SpecParamDTO;
import com.phpsong.product.service.dao.product.SpecGroupMapper;
import com.phpsong.product.service.dao.product.SpecParamMapper;
import com.phpsong.product.service.domain.entity.product.SpecGroup;
import com.phpsong.product.service.domain.entity.product.SpecParam;
import com.phpsong.product.service.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bystander
 * @date 2018/9/18
 */
@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;


    @Override
    public List<SpecGroupDTO> querySpecGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroupList = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(specGroupList)) {
            throw new ShopException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }

        return BeanUtil.copyList(specGroupList, SpecGroupDTO.class);
    }

    @Override
    public void saveSpecGroup(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = BeanUtil.copyProperties(specGroupDTO, SpecGroup.class);
        int count = specGroupMapper.insert(specGroup);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.SPEC_GROUP_CREATE_FAILED);
        }
    }

    @Override
    public void deleteSpecGroup(Long id) {
        if (id == null) {
            throw new ShopException(ExceptionEnum.INVALID_PARAM);
        }
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int count = specGroupMapper.deleteByPrimaryKey(specGroup);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.DELETE_SPEC_GROUP_FAILED);
        }
    }

    @Override
    public void updateSpecGroup(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = BeanUtil.copyProperties(specGroupDTO, SpecGroup.class);
        int count = specGroupMapper.updateByPrimaryKey(specGroup);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.UPDATE_SPEC_GROUP_FAILED);
        }
    }


    @Override
    public List<SpecParamDTO> querySpecParams(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
        List<SpecParam> specParamList = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(specParamList)) {
            throw new ShopException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }

        return BeanUtil.copyList(specParamList, SpecParamDTO.class);
    }

    @Override
    public void saveSpecParam(SpecParamDTO specParamDTO) {
        SpecParam specParam = BeanUtil.copyProperties(specParamDTO, SpecParam.class);
        int count = specParamMapper.insert(specParam);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.SPEC_PARAM_CREATE_FAILED);
        }
    }

    @Override
    public void updateSpecParam(SpecParamDTO specParamDTO) {
        SpecParam specParam = BeanUtil.copyProperties(specParamDTO, SpecParam.class);
        int count = specParamMapper.updateByPrimaryKeySelective(specParam);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.UPDATE_SPEC_PARAM_FAILED);
        }
    }

    @Override
    public void deleteSpecParam(Long id) {
        if (id == null) {
            throw new ShopException(ExceptionEnum.INVALID_PARAM);
        }
        int count = specParamMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new ShopException(ExceptionEnum.DELETE_SPEC_PARAM_FAILED);
        }
    }

    @Override
    public List<SpecGroupDTO> querySpecsByCid(Long cid) {
        List<SpecGroupDTO> specGroups = querySpecGroupByCid(cid);

        List<SpecParamDTO> specParams = querySpecParams(null, cid, null, null);

        Map<Long, List<SpecParamDTO>> map = new HashMap<>();
        //遍历specParams
        for (SpecParamDTO param : specParams) {
            Long groupId = param.getGroupId();
            if (!map.keySet().contains(param.getGroupId())) {
                //map中key不包含这个组ID
                map.put(param.getGroupId(), new ArrayList<>());
            }
            //添加进map中
            map.get(param.getGroupId()).add(param);
        }

        for (SpecGroupDTO specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }

        return specGroups;
    }
}


