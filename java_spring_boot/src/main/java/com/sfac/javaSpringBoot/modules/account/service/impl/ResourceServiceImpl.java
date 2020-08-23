package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.dao.ResourceDao;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.account.service.ResourceService;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;

    @Override
    public PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<Resource>(
                Optional.ofNullable(resourceDao.getResourcesBySearchVo(searchVo))
                .orElse(Collections.emptyList())
        );
    }
}
