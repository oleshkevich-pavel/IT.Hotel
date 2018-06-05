package com.itacademy.jd2.po.hotel.web.tag;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.itacademy.jd2.po.hotel.web.util.XMLResourceBundleControl;

public class I18N extends SimpleTagSupport {

    public static final Locale LOCALE_RU = new Locale("ru");
    public static final Locale LOCALE_EN = new Locale("en");
    public static final Locale DEFAULT_LOCALE = new Locale("en");
    public static final String SESSION_LOCALE_KEY = "current-locale";
    public static final Map<Locale, Map<String, String>> DICTIONARY = new HashMap<Locale, Map<String, String>>();
    private String key;

    static {
        loadDictionary(LOCALE_RU);
        loadDictionary(LOCALE_EN);
    }

    private static void loadDictionary(final Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/dictionary", locale, new XMLResourceBundleControl());
        Map<String, String> dictionary = new HashMap<String, String>();
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = bundle.getString(key);
            dictionary.put(key, value);
        }
        DICTIONARY.put(locale, dictionary);
    }

    @Override
    public void doTag() throws JspException, IOException {
/*        final JspContext jspContext = getJspContext();
        Locale currentSessionLocale = (Locale) ((PageContext) jspContext).getSession().getAttribute(SESSION_LOCALE_KEY);
        // Locale currentSessionLocale = (Locale)
        // jspContext.findAttribute(SESSION_LOCALE_KEY);
        final HttpSession session = ((PageContext) jspContext).getSession();

        if ((!AuthHelper.isUserAnonymous()) && (currentSessionLocale == null)) {
            ILocaleService localeService = (ILocaleService) SpringApplicationContext.getBean(ILocaleService.class);
            Integer userAccountId = AuthHelper.getLoggedUserId();
            Locale userLocale = localeService.get(userAccountId);
            if (userLocale != null) {
                currentSessionLocale = userLocale;
                session.setAttribute(SESSION_LOCALE_KEY, userLocale);
            }
        }

        if (currentSessionLocale == null) {
            session.setAttribute(SESSION_LOCALE_KEY, DEFAULT_LOCALE);
            currentSessionLocale = DEFAULT_LOCALE;

             jspContext.getOut().println(DICTIONARY.get(currentSessionLocale).get(key));
        }*/
        final JspContext jspContext = getJspContext();
        final HttpSession session = ((PageContext) jspContext).getSession();

        Locale locale = (Locale) jspContext.findAttribute(SESSION_LOCALE_KEY);
        if (locale == null) {
            locale = DEFAULT_LOCALE;
            session.setAttribute(SESSION_LOCALE_KEY, locale);
        }
        jspContext.getOut().println(DICTIONARY.get(locale).get(key));

    }

    /*
     * @Override public void doTag2() throws JspException, IOException { final
     * JspContext jspContext = getJspContext(); Locale locale = (Locale)
     * jspContext.findAttribute(SESSION_LOCALE_KEY); final HttpSession session =
     * ((PageContext) getJspContext()).getSession(); Locale locale2 =
     * BUNDLE.getLocale(); if ((locale != null) && (!locale2.equals(locale))) {
     * BUNDLE = ResourceBundle.getBundle("i18n/dictionary", locale, new
     * XMLResourceBundleControl()); loadDictionary(BUNDLE); } if ((locale ==
     * null) && (AuthHelper.isUserAnonymous())) {
     *
     * session.setAttribute(SESSION_LOCALE_KEY, DEFAULT_LOCALE); //
     * jspContext.setAttribute(SESSION_LOCALE_KEY, DEFAULT_LOCALE, //
     * getJspContext().get SESSION); loadDictionary(BUNDLE); } if ((locale ==
     * null) && (!AuthHelper.isUserAnonymous())) { ILocaleService localeService
     * = (ILocaleService)
     * SpringApplicationContext.getBean(ILocaleService.class); Integer
     * userAccountId = AuthHelper.getLoggedUserId(); Locale userLocale =
     * localeService.get(userAccountId); if (userLocale == null) { //
     * jspContext.setAttribute(SESSION_LOCALE_KEY, DEFAULT_LOCALE, //
     * PageContext.SESSION); session.setAttribute(SESSION_LOCALE_KEY,
     * DEFAULT_LOCALE); localeService.save(DEFAULT_LOCALE, userAccountId);
     * loadDictionary(BUNDLE); } else { //
     * jspContext.setAttribute(SESSION_LOCALE_KEY, userLocale, //
     * PageContext.SESSION); session.setAttribute(SESSION_LOCALE_KEY,
     * userLocale); BUNDLE = ResourceBundle.getBundle("i18n/dictionary",
     * userLocale, new XMLResourceBundleControl()); loadDictionary(BUNDLE); } }
     * jspContext.getOut().println(DICTIONARY.get(key)); }
     */

    public void setKey(final String key) {
        this.key = key;
    }

}
