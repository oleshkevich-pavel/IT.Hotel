package com.itacademy.jd2.po.hotel.service;

public interface IReCaptchaService {

    String ERROR_STRING = "Captcha is invalid!";

    boolean verify(String gRecaptchaResponse);
}
