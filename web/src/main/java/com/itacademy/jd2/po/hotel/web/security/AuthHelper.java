package com.itacademy.jd2.po.hotel.web.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    private AuthHelper() {
    }

    public static Integer getLoggedUserId() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final ExtendedUsernamePasswordAuthenticationToken authentication = (ExtendedUsernamePasswordAuthenticationToken) context
                .getAuthentication();
        return authentication.getId();
    }

    public static String getLoggedUserRole() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final ExtendedUsernamePasswordAuthenticationToken authentication = (ExtendedUsernamePasswordAuthenticationToken) context
                .getAuthentication();
        return authentication.getAuthorities().toString();
    }

    public static boolean isUserAnonymous() {
        return (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() == "anonymousUser");
    }
}
