package com.safc.springcloud.springCloudClientAccount.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.safc.springcloud.springCloudClientAccount.modules.account.entity.User;
import com.safc.springcloud.springCloudClientAccount.modules.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 127.0.0.1/api/user/1 ---- get
     */
    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable int userId) {
        return userService.getUserByUserId(userId);
    }

}
