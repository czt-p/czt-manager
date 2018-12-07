package com.zjcds.czt.manager.service.impl;

import com.zjcds.czt.manager.service.AbstractAuthorizeService;
import com.zjcds.czt.manager.utils.WebSecurityUtils;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public class RootRoleAuthorizeService extends AbstractAuthorizeService {

    private final static String DefaultPattern = "/**";

    public RootRoleAuthorizeService() {
        super(Integer.MIN_VALUE, DefaultPattern);
    }

    @Override
    public boolean check(Authentication authentication, HttpServletRequest request) {
        return WebSecurityUtils.currentUserIsRoot();
    }
}
