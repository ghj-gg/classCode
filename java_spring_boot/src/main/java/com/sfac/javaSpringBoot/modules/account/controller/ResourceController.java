package com.sfac.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.account.service.ResourceService;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 127.0.0.1/api/resources ----------- post
     * {"currentPage":"1","pageSize":"5","keyWord":"u"}
     */
    @PostMapping(value = "resources",consumes = MediaType.APPLICATION_JSON_VALUE)
    PageInfo<Resource> getResourcesBySearchVo(@RequestBody SearchVo searchVo){
        return resourceService.getResourcesBySearchVo(searchVo);
    }
}
