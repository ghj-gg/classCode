package com.sfac.javaSpringBoot.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * 127.0.0.1/account/login ------ get
     */
    @GetMapping("/login")
    public String login(){
        return "indexSimple";
    }

    /**
     * 127.0.0.1/account/register ------ get
     */
    @GetMapping("/register")
    public String register(){
        return "indexSimple";
    }

    /**
     * 127.0.0.1/account/users ----- get
     */
    @GetMapping("/users")
    public String usersPage(){
        return "index";
    }

    /**
     * 127.0.0.1/account/profile -----  get
     */
    @GetMapping("/profile")
    public String profilePage(){
        return "index";
    }

    /**
     * 127.0.0.1/account/registerVue ------ get
     */
    @GetMapping("/registerVue")
    public String registerVuePage(){
        return "indexSimple";
    }
}
