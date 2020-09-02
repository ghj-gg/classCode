package com.sfac.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.common.vo.Result;

import java.util.List;

public interface ResourceService {

    PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);

    Result<Resource> insertResource(Resource resource);

    List<Resource> getResourcesByRoleId(int roleId);

    Result<Resource> updateResource(Resource resource);

    Result<Object> deleteResource(int resourceId);

    Resource getResourceByResourceId(int resourceId);

}
