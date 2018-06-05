package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.po.hotel.service.ILocaleService;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;
import com.itacademy.jd2.po.hotel.web.tag.I18N;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    @Autowired
    private ILocaleService localeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final HttpServletRequest req,
            @RequestParam(name = "language", required = false) final String lang) {
        if (!AuthHelper.isUserAnonymous()) {
            Integer userAccountId = AuthHelper.getLoggedUserId();
            Locale userLocale = localeService.get(userAccountId);
            if (userLocale != null) {
                req.getSession().setAttribute(I18N.SESSION_LOCALE_KEY, userLocale);
            }
        }
        Locale currentLocale =(Locale) req.getSession().getAttribute(I18N.SESSION_LOCALE_KEY); 
        if (currentLocale == null) {req.getSession().setAttribute(I18N.SESSION_LOCALE_KEY, I18N.DEFAULT_LOCALE);}
        if (lang != null) {
            setLocale(req, lang);
        }
        return "index";
    }

    private void setLocale(final HttpServletRequest req, final String lang) {
        Locale locale = I18N.LOCALE_EN;
        if ("ru".equals(lang)) {
            locale = I18N.LOCALE_RU;
        }
        req.getSession().setAttribute(I18N.SESSION_LOCALE_KEY, locale);
        if (!AuthHelper.isUserAnonymous()) {
            Integer userAccountId = AuthHelper.getLoggedUserId();
            localeService.save(locale, userAccountId);
        }
    }
}
