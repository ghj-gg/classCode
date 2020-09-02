package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.dao.RoleDao;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.account.service.RoleService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getRoles() {
        return Optional.ofNullable(roleDao.getRoles()).orElse(Collections.emptyList());
    }

    @Override
    public PageInfo<Role> getRoleBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<Role>(
                Optional.ofNullable(roleDao.getRoleBySearchVo(searchVo))
                        .orElse(Collections.emptyList())
        );
    }

    @Override
    @Transactional
    public Result<Role> updateRole(Role role) {
        Role roleTemp = roleDao.getRoleByRoleName(role.getRoleName());
        if (roleTemp != null && roleTemp.getRoleId() != role.getRoleId()){
            return new Result<Role>(
                    Result.ResultStatus.FAILD.status,"Role name is repeat."
            );
        }

        roleDao.updateRole(role);

        return new Result<Role>(
                Result.ResultStatus.SUCCESS.status,"Update success.",role);
    }

    @Override
    @Transactional
    public Result<Object> deleteRole(int roleId) {
        roleDao.deleteRole(roleId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"Delete success.");
    }

    @Override
    public Result<Role> insertRole(Role role) {
        Role roleTemp = roleDao.getRoleByRoleName(role.getRoleName());
        if (roleTemp != null){
            return new Result<Role>(
                    Result.ResultStatus.FAILD.status,"Role name is repeat."
            );
        }
        roleDao.insertRole(role);
        return new Result<Role>(Result.ResultStatus.SUCCESS.status,"Insert role success.",role);
    }

    @Override
    public Role getRolesByRoleId(int roleId) {
       return roleDao.getRolesByRoleId(roleId);
    }
}
