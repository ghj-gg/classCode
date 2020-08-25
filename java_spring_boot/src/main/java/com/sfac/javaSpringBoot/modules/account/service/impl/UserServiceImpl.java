package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.config.ResourceConfigBean;
import com.sfac.javaSpringBoot.modules.account.dao.UserDao;
import com.sfac.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.account.service.UserService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private ResourceConfigBean resourceConfigBean;

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
        Subject subject = SecurityUtils.getSubject();

        //令牌
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(user.getUserName(),MD5Util.getMD5(user.getPassword()));
        usernamePasswordToken.setRememberMe(user.getRememberMe());

        try {
            subject.login(usernamePasswordToken);
            subject.checkRoles();
        }catch (Exception e){
            return new Result<User>(Result.ResultStatus.FAILD.status,"User name or password error.");
        }

        Session session = subject.getSession();
        session.setAttribute("user",(User)subject.getPrincipal());

        return new Result<User>(Result.ResultStatus.SUCCESS.status,"Login success.");

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

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
        if (file.isEmpty()){
            return new Result<String>(
                    Result.ResultStatus.FAILD.status,"Please select img.");
        }
        String relativePath = "";
        try {
            String destFilePath = "";
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("win")){
                destFilePath = resourceConfigBean.getLocationPathForWindows()
                        + file.getOriginalFilename();
            }else {
                destFilePath = resourceConfigBean.getLocationPathForLinux()
                        + file.getOriginalFilename();
            }
            relativePath = resourceConfigBean.getRelativePath() +
                    file.getOriginalFilename();

            File destFile = new File(destFilePath);
            file.transferTo(destFile);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result<String>(Result.ResultStatus.FAILD.status,"Upload failed.");
        }
        return new Result<String>(
                Result.ResultStatus.SUCCESS.status,"Upload success.",relativePath);
    }

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
        //判断传入的user是否存在
        User userTemp = userDao.selectUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getUserId() != user.getUserId()){
            return new Result<User>(Result.ResultStatus.FAILD.status,"User name is repeat.");
        }
        userDao.updateUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status,"update success.",user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Session session = subject.getSession();
        session.setAttribute("user",null);
    }
}
