package com.sfac.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;

import java.util.List;

public interface ResourceService {

    PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);

    List<Resource> getResourcesByRoleId(int roleId);
}
