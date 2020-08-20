package com.sfac.javaSpringBoot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;

import java.util.List;

public interface UserService {

    public Result<User> insertUser(User user);

    public Result<User> login(User user);

    PageInfo<User> getUserBySearchVo(SearchVo searchVo);
}
