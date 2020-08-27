package com.sfac.javaSpringBoot.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

    /**
     * 127.0.0.1/common/dashboard
     */
    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "index";
    }

    /**
     * 127.0.0.1/common/403 ---- get
     */
    @GetMapping("/403")
    public String errorPageFor403(){
        return "index";
    }
}
