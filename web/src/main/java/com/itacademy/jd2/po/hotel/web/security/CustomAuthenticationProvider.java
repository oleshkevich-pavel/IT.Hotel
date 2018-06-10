package com.itacademy.jd2.po.hotel.web.security;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger("loginLogger");

    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final String email = authentication.getPrincipal().toString();
        final String password = authentication.getCredentials().toString();
        LOGGER.info("user {} try to log in.", email);
        IUserAccount user = userAccountService.getByEmail(email);
        if (user == null) {
            LOGGER.info("user {} doesn't exist.");
            throw new BadCredentialsException("1000");

        } else {
         // если гость не подтвердил e-mail доступа ему нет
            if ((user.getGuest() != null) && (!user.getGuest().getVerified())) {
                throw new DisabledException("1001");
            }
            if (!userAccountService.isPasswordCorrect(user, password)) {
                LOGGER.info("incorrect password for user {}", email);
                throw new BadCredentialsException("1000");
            }
            final int userId = user.getId();

            final List<SimpleGrantedAuthority> roles = Arrays
                    .asList(new SimpleGrantedAuthority(user.getRole().toString()));
            LOGGER.info("user {} with role {} has been logged.", user.getEmail(), user.getRole());
            return new ExtendedUsernamePasswordAuthenticationToken(userId, email, password, roles);
        }

        /*
         * if (user.getLocked()) { // locked user. если юзер забанен throw new
         * DisabledException("1001"); }
         */
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
