package com.phpsong.product.service.service;

import com.phpsong.product.api.dto.SpecGroupDTO;
import com.phpsong.product.api.dto.SpecParamDTO;

import java.util.List;

/**
 * @author bystander
 * @date 2018/9/18
 */
public interface SpecService {

    List<SpecGroupDTO> querySpecGroupByCid(Long cid);

    void saveSpecGroup(SpecGroupDTO specGroup);

    void deleteSpecGroup(Long id);

    void updateSpecGroup(SpecGroupDTO specGroup);

    List<SpecParamDTO> querySpecParams(Long gid, Long cid, Boolean searching, Boolean generic);

    void saveSpecParam(SpecParamDTO specParam);

    void updateSpecParam(SpecParamDTO specParam);

    void deleteSpecParam(Long id);

    List<SpecGroupDTO> querySpecsByCid(Long cid);
}
