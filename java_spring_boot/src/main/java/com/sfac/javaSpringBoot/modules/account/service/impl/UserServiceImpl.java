package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.dao.UserDao;
import com.sfac.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.account.service.UserService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    @Transactional
    public Result<User> insertUser(User user) {
        //重名判断
        User userTemp = userDao.selectUserByUserName(user.getUserName());
        if (userTemp != null){
            return new Result<User>(Result.ResultStatus.FAILD.status,"User name is repeat.");
        }

        //初始化，插入User
        user.setCreateDate(LocalDateTime.now());
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        userDao.insertUser(user);

        //添加角色信息
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()){
            //for (int i = 0;i <roles.size();i++){
            //    userRoleDao.insertUserRole(user.getUserId(),roles.get(i).getRoleId());
            //}
            //for(Role role:roles){
            //    userRoleDao.insertUserRole(user.getUserId(),role.getRoleId());
            //}
            roles.stream().forEach(item -> {
                userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
            });
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status,"Insert success.",user);
    }

    @Override
    public Result<User> login(User user) {
        User userTemp = userDao.selectUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
            return new Result<User>(Result.ResultStatus.SUCCESS.status,"Login success.");
        }
        return new Result<User>(Result.ResultStatus.FAILD.status,"User name or password error.");
    }

    @Override
    public PageInfo<User> getUserBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUserBySearchVo(searchVo))
                .orElse(Collections.emptyList())
        );
    }

    @Override
    @Transactional
    public Result<User> updateUser(User user) {
        //判断传入的user是否存在
        User userTemp = userDao.selectUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getUserId() != user.getUserId()){
            return new Result<User>(Result.ResultStatus.FAILD.status,"User name is repeat.");
        }
        userDao.updateUser(user);

        //添加角色信息
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()){
            //for (int i = 0;i <roles.size();i++){
            //    userRoleDao.insertUserRole(user.getUserId(),roles.get(i).getRoleId());
            //}
            //for(Role role:roles){
            //    userRoleDao.insertUserRole(user.getUserId(),role.getRoleId());
            //}
            roles.stream().forEach(item -> {
                userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
            });
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status,"Update success.",user);
    }

    @Override
    @Transactional
    public Result<Object> deleteUser(int userId) {
        userDao.deleteUser(userId);
        userRoleDao.deleteUserRoleByUserId(userId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"Delete success.");
    }

    @Override
    public User getUserByUserId(int userId) {
        return userDao.getUserByUserId(userId);
    }
}
