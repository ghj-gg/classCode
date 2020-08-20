package com.sfac.javaSpringBoot.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * 密码加密
 */
public class MD5Util {

    private static final String SALT = "&&&*****%%%%%@#";

    public static String getMD5(String str){
        if (StringUtils.isBlank(str)){
            return null;
        }
        String base = str + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
