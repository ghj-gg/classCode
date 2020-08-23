package com.sfac.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.account.service.RoleService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 127.0.0.1/api/roles ------ get
     */
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }

    /**
     * 127.0.0.1/api/roles ----  post
     * {"currentPage":"1","pageSize":"5","keyWord":"a"}
     */
    @PostMapping(value = "/roles",consumes = MediaType.APPLICATION_JSON_VALUE)
    PageInfo<Role> getRoleBySearchVo(@RequestBody SearchVo searchVo){
        return roleService.getRoleBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1/api/role ------ post
     * {"roleName":"js1"}
     */
    @PostMapping(value = "/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<Role> insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    /**
     * 127.0.0.1/api/role ----- put
     * {"roleName":"js2","roleId":"4"}
     */
    @PutMapping(value = "/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<Role> updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    /**
     * 127.0.0.1/api/role/4 ----- delete
     */
    @DeleteMapping("/role/{roleId}")
    Result<Object> deleteRole(@PathVariable int roleId){
        return roleService.deleteRole(roleId);
    }

    /**
     * 127.0.0.1/api/role/3 -----get
     */
    @GetMapping("/role/{roleId}")
    List<Role> getRolesByRoleId(@PathVariable int roleId){
        return roleService.getRolesByRoleId(roleId);
    }
}
