package com.sfac.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.account.service.UserService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 127.0.0.1/api/user -----  post
     * {"userName":"admin","password":"111111"}
     */
    @PostMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    /**
     * 127.0.0.1/api/login -----  post
     *{"userName":"admin","password":"111111"}
     */
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> login(@RequestBody User user){
        return userService.login(user);
    }

    /**
     * 127.0.0.1/api/users  ------  post
     * {"currentPage":"1","pageSize":"5","keyWord":"ghj"}
     */
    @PostMapping(value = "/users",consumes = MediaType.APPLICATION_JSON_VALUE)
    PageInfo<User> getUserBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUserBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1/api/user -------   put
     * {"userName":"ghj1","userImg":"/aaa.jpg","userId":"3"}
     */
    @PutMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 127.0.0.1/api/user/1 ------- delete
     */
    @DeleteMapping("/user/{userId}")
    public Result<Object> deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }

    /**
     * 127.0.0.1/api/user/3 --------  get
     */
    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable int userId){
        return userService.getUserByUserId(userId);
    }

    /**
     * 127.0.0.1/api/userImg ----- post
     */
    @PostMapping(value = "/userImg",consumes = "multipart/form-data")
    public Result<String> uploadFiles(@RequestParam MultipartFile file){
        return userService.uploadUserImg(file);
    }

    /**
     * 127.0.0.1/api/profile ---- put
     */
    @PutMapping(value = "/profile", consumes = "application/json")
    public Result<User> updateUserProfile(@RequestBody User user){
        return userService.updateUserProfile(user);
    }
}
