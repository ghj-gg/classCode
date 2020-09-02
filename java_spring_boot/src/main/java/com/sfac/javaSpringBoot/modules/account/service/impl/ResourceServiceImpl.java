package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.dao.ResourceDao;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.account.service.ResourceService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sfac.javaSpringBoot.modules.account.dao.ResourceRoleDao;
import com.sfac.javaSpringBoot.modules.account.entity.Role;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private ResourceRoleDao resourceRoleDao;

    @Override
    public PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<Resource>(
                Optional.ofNullable(resourceDao.getResourcesBySearchVo(searchVo))
                .orElse(Collections.emptyList())
        );
    }

    @Override
    @Transactional
    public Result<Resource> insertResource(Resource resource) {

        resourceDao.insertResource(resource);

        resourceRoleDao.deleteResourceRoleByResourceId(resource.getResourceId());
        List<Role> roles = resource.getRoles();
        if (roles != null && !roles.isEmpty()){
            roles.stream().forEach(item -> {
                resourceRoleDao.insertResourceRole(resource.getResourceId(),item.getRoleId());
            });
        }

        return new Result<Resource>(Result.ResultStatus.SUCCESS.status,"Insert success.",resource);
    }

    @Override
    @Transactional
    public Result<Resource> updateResource(Resource resource) {

        resourceDao.updateResource(resource);

        resourceRoleDao.deleteResourceRoleByResourceId(resource.getResourceId());
        List<Role> roles = resource.getRoles();
        if (roles != null && !roles.isEmpty()){
            roles.stream().forEach(item -> {
                resourceRoleDao.insertResourceRole(resource.getResourceId(),item.getRoleId());
            });
        }

        return new Result<Resource>(Result.ResultStatus.SUCCESS.status,"update success.",resource);
    }

    @Override
    @Transactional
    public Result<Object> deleteResource(int resourceId) {
        resourceDao.deleteResource(resourceId);
        resourceRoleDao.deleteResourceRoleByResourceId(resourceId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"Delete success.");
    }

    @Override
    public Resource getResourceByResourceId(int resourceId) {
        return resourceDao.getResourceByResourceId(resourceId);
    }

    @Override
    public List<Resource> getResourcesByRoleId(int roleId) {
        return resourceDao.getResourcesByRoleId(roleId);
    }


}
