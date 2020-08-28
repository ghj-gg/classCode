package com.safc.springcloud.springCloudClientAccount.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safc.springcloud.springCloudClientAccount.modules.account.dao.UserDao;
import com.safc.springcloud.springCloudClientAccount.modules.account.entity.City;
import com.safc.springcloud.springCloudClientAccount.modules.account.entity.User;
import com.safc.springcloud.springCloudClientAccount.modules.account.service.UserService;
import com.safc.springcloud.springCloudClientAccount.modules.common.vo.Result;
import com.safc.springcloud.springCloudClientAccount.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
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
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public Result<User> insertUser(User user) {

        return new Result<User>(Result.ResultStatus.SUCCESS.status,
                "Login success.", user);

    }

    @Override
    public Result<User> login(User user) {
        return null;
    }

    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                    .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<User> updateUser(User user) {


        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "Update success.", user);
    }

    @Override
    @Transactional
    public Result<Object> deleteUser(int userId) {
        return new Result<>(Result.ResultStatus.SUCCESS.status, "Delete success.");
    }

    @Override
    public User getUserByUserId(int userId) {
        User user = userDao.getUserByUserId(userId);
        List<City> cities = Optional
                .ofNullable(restTemplate.getForObject(
                "http://CLIENT-TEST/api/cities/{countryId}",
                    List.class, 522))
                .orElse(Collections.emptyList());
        user.setCities(cities);
        return user;
    }

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
//        if (file.isEmpty()) {
//            return new Result<String>(
//                    Result.ResultStatus.FAILED.status, "Please select img.");
//        }
//
//        String relativePath = "";
//        String destFilePath = "";
//        try {
//            String osName = System.getProperty("os.name");
//            if (osName.toLowerCase().startsWith("win")) {
//                destFilePath = resourceConfigBean.getLocationPathForWindows() +
//                        file.getOriginalFilename();
//            } else {
//                destFilePath = resourceConfigBean.getLocationPathForLinux()
//                        + file.getOriginalFilename();
//            }
//            relativePath = resourceConfigBean.getRelativePath() +
//                    file.getOriginalFilename();
//            File destFile = new File(destFilePath);
//            file.transferTo(destFile);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Result<String>(
//                    Result.ResultStatus.FAILED.status, "Upload failed.");
//        }

        return new Result<String>(
                Result.ResultStatus.SUCCESS.status, "Upload success.", "");
    }

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "User name is repeat.");
        }

        userDao.updateUser(user);

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Edit success.", user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public void logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        Session session = subject.getSession();
//        session.setAttribute("user", null);
    }
}
