package com.sfac.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.account.service.ResourceService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 127.0.0.1/api/resource ---- post
     * {"resourceUri":"127.0.0.1/api/user/{userId}","resourceName":"get","permission":"/api/user/{userId}"}
     */
    @PostMapping(value = "/resource",consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<Resource> insertResource(@RequestBody Resource resource){
        return resourceService.insertResource(resource);
    }

    /**
     * 127.0.0.1/api/resource ---- put
     * {"resourceUri":"127.0.0.1/api/user/{userId}","resourceName":"get","permission":"/api/user/{userId}","resourceId":"4"}
     */
    @PutMapping(value = "/resource",consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<Resource> updateResource(@RequestBody Resource resource){
        return resourceService.updateResource(resource);
    }

    /**
     * 127.0.0.1/api/resource/{resourceId} ---- get
     */
    @GetMapping("/resource/{resourceId}")
    Resource getResourceByResourceId(@PathVariable int resourceId){
        return resourceService.getResourceByResourceId(resourceId);
    }

    /**
     * 127.0.0.1/api/resource/{resourceId} ---- delete
     */
    @DeleteMapping("/resource/{resourceId}")
    Result<Object> deleteResource(@PathVariable int resourceId){
        return resourceService.deleteResource(resourceId);
    }
}
