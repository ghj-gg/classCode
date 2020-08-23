package com.sfac.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;

import java.util.List;

public interface RoleService {

    List<Role> getRoles();

    PageInfo<Role> getRoleBySearchVo(SearchVo searchVo);

    Result<Role> updateRole(Role role);

    Result<Object> deleteRole(int roleId);

    Result<Role> insertRole(Role role);

    List<Role> getRolesByRoleId(int roleId);
}
