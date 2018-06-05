package com.itacademy.jd2.po.hotel.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IEmailSenderService;

@Component
public class EmailSenderServiceImpl implements IEmailSenderService {

    @Autowired
    private Session mailSession;

    private void sendEmail(final String to, final String subject, final String textBody, final String htmlBody) {
        final MimeMessage message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            final InternetAddress[] address = { new InternetAddress(to) };
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());

            final Multipart content = new MimeMultipart("alternative");
            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(textBody, "text/plain");
            content.addBodyPart(textPart);
            final MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html;charset=\"UTF-8\"");
            message.setSubject(subject, StandardCharsets.UTF_8.name());
            content.addBodyPart(htmlPart);
            message.setContent(content);
            Transport.send(message);
        } catch (final MessagingException ex) {
            ex.printStackTrace();
            // TODO catch
        }
    }

    @Override
    public void sendEmailFromWebSite(final IMessage entity) {
        final String name = entity.getName();
        final String phone = entity.getPhone();
        final String emailFrom = entity.getEmail();
        final String message = entity.getMessage();
        final String subject = "Message for Shmotel from " + name;
        final String text = String.format("You have a message:\n name: %s, phone: %s, email: %s\n\n Message:\n%s", name,
                phone, emailFrom, message);
        final String html = String.format("You have a message:<br> name: %s, phone: %s, email: %s<br><br> Message:\n%s",
                name, phone, emailFrom, message);
        sendEmail(mailSession.getProperty("mail.from"), subject, text, html);

    }

    @Override
    public void sendVerifyKey(final IUserAccount userAccount, final String verifyKey) {
        final String email = userAccount.getEmail();
        final String subject = "Verify key from Shmotel";
        final String name = userAccount.getFirstName() + " " + userAccount.getLastName();
        final String url = "http://localhost:8081/hotel/registration/verifying/";
        final String fullUrl = url + verifyKey;
        final String text = String.format("Dear %s, thanks for registering. \n Your verify key: %s"
                + "\n You can enter it here:%s. Or you can go with link: %s"
                + "\n\n Don't answer this e-mail, please! ", name, verifyKey, url, fullUrl);
        final String html = String.format("Dear %s, thanks for registering. <br> Your verify key: %s"
                + "<br> You can enter it <a href=\"%s\">here</a>. Or you can go with link: <a href=\"%s\">%s</a>"
                + "<br><br> Don't answer this e-mail, please! ", name, verifyKey, url, fullUrl, fullUrl);
        sendEmail(email, subject, text, html);

    }
}
