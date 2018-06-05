package com.itacademy.jd2.po.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IEmailSenderService;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IReCaptchaService;
import com.itacademy.jd2.po.hotel.web.converter.GuestFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.GuestDTO;
import com.itacademy.jd2.po.hotel.web.dto.search.GuestSearchDTO;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger("registrationLogger");
    private static final String SEARCH_FORM_MODEL = "searchFormModel";

    @Autowired
    private IGuestService guestService;
    @Autowired
    private GuestFromDTOConverter fromDTOConverter;
    @Autowired
    private IEmailSenderService emailSenderService;
    @Autowired
    private IReCaptchaService reCaptchaService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        final Map<String, Object> hashMap = new HashMap<>();
        final GuestDTO dto = new GuestDTO();
        hashMap.put("formModel", dto);
        return new ModelAndView("registration.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(final HttpServletRequest req, @Valid @ModelAttribute("formModel") final GuestDTO formModel,
            final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();

        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            return new ModelAndView("registration.edit", hashMap);
        } else {

            String gRecaptchaResponse = req.getParameter("g-recaptcha-response"); // Verify
                                                                                  // CAPTCHA.
            boolean valid = reCaptchaService.verify(gRecaptchaResponse);
            if (!valid) { // req.setAttribute("errorString", "Captcha
                          // invalid!");
                hashMap.put("error", reCaptchaService.ERROR_STRING);
                LOGGER.info(reCaptchaService.ERROR_STRING);
                return new ModelAndView("registration.edit", hashMap);
            }

            final IGuest entity = fromDTOConverter.apply(formModel);
            final IUserAccount userAccount = entity.getUserAccount();
            final String verifyKey = generateString(20);
            entity.setVerifyKey(verifyKey);
            try {
                guestService.save(userAccount, entity);
                emailSenderService.sendVerifyKey(userAccount, verifyKey);
                LOGGER.info("verify key {} was send for user {}", verifyKey, userAccount.getEmail());
            } catch (PersistenceException e) {
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("registration.edit", hashMap);
            }
            return "redirect:registration/verifying";
        }
    }

    @RequestMapping(value = "/verifying/{verifyKey}", method = RequestMethod.GET)
    public Object verifyWithLink(@PathVariable(name = "verifyKey") final String verifyKey,
            @ModelAttribute(SEARCH_FORM_MODEL) final GuestSearchDTO searchDTO) {

        final boolean verified = isVerifyingComplete(verifyKey);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("verified", verified);
        return new ModelAndView("registration.verifying", hashMap);
    }

    @RequestMapping(value = "/verifying", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView verifyWithKey(@ModelAttribute(SEARCH_FORM_MODEL) final GuestSearchDTO searchDTO) {

        String verifyKey = searchDTO.getVerifyKey();

        final boolean verified = isVerifyingComplete(verifyKey);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("verified", verified);
        return new ModelAndView("registration.verifying", hashMap);
    }

    private boolean isVerifyingComplete(final String verifyKey) {
        final GuestFilter filter = new GuestFilter();
        if (verifyKey != null) {
            filter.setVerifyKey(verifyKey);
        }
        filter.setFetchGuestStatus(true);
        filter.setFetchUserAccount(true);
        final List<IGuest> entities = guestService.find(filter);

        if (entities.size() == 1) {
            IGuest entity = entities.get(0);
            entity.setVerified(true);
            guestService.save(entity.getUserAccount(), entity);
            LOGGER.info("verifying complete for user {}", entity.getUserAccount().getEmail());
            return true;
        } else {
            return false;
        }

    }

    private String generateString(final int length) {
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
        Random rnd = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }
        return new String(text);
    }
}
