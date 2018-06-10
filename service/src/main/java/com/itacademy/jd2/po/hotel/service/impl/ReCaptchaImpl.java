package com.itacademy.jd2.po.hotel.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.service.IReCaptchaService;

@Service
public class ReCaptchaImpl implements IReCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger("verifyReCaptchaLogger");

    private static final String SITE_KEY = "6LcjfVoUAAAAANHUeelMXznTDWOMpjVUS6x3sWBg";
    private static final String SECRET_KEY = "6LcjfVoUAAAAAATyxk0gyV3RtP3SCX5x5n_8BRN4";
    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
   // public static final String ERROR_STRING = "Captcha is invalid!";

    public boolean verify(final String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }
        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);
            // Открыть соединение (Connection) к URL выше.
            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();
            // Добавить информации Header в Request, чтобы приготовить отправку
            // к server.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Данные будут отправлены на Server.
            String postParams = String.format("secret=%s&response=%s", SECRET_KEY, gRecaptchaResponse);
            // Send Request
            conn.setDoOutput(true);
            // Получить Output Stream (Выходной поток) соединения к Server.
            // Записать данные в Output Stream, значит отправить информацию на
            // Server.
            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            // Ответный код возвращает из Server.
            int responseCode = conn.getResponseCode();
            LOGGER.info("responseCode=" + responseCode);
            // Получить Input Stream (Входной поток) Connection
            // чтобы прочитать данные отправленные от Server.
            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            // ==> {"success": true}
            LOGGER.info("Response: " + jsonObject);

            boolean success = jsonObject.getBoolean("success");
            if (!success) {
                LOGGER.info(ERROR_STRING);
            }
            return success;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
    }
}
