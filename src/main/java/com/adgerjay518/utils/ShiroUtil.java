package com.adgerjay518.utils;

import com.adgerjay518.shiro.Profile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static Profile getProfile() {
        return (Profile) SecurityUtils.getSubject().getPrincipal();
    }

}
