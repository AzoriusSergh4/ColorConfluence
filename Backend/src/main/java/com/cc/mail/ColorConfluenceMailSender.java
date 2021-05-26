package com.cc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ColorConfluenceMailSender {

    private static final String LOCAL = "http://localhost:4200";
    private static final String PROD = "https://colorconfluence.ddns.net";

    @Autowired
    JavaMailSender mailSender;

    /**
     * Sends an email with a link to confirm the account creation
     *
     * @param to    the destination account
     * @param token the token to send
     */
    public void sendValidationAccount(String to, String token) {
        var message = new SimpleMailMessage();
        message.setFrom("colorconfluence@gmail.com");
        message.setTo(to);
        message.setSubject("ColorConfluence Account Verification!");
        message.setText("You almost finished! To confirm your account, please click the link below: \n" + LOCAL + "/confirm-account?tk=" + token);
        mailSender.send(message);
    }

    public void sendRecoverPassword(String to, String token) {
        var message = new SimpleMailMessage();
        message.setFrom("colorconfluence@gmail.com");
        message.setTo(to);
        message.setSubject("ColorConfluence Recover Password");
        message.setText("To recover your password, please click the link below: \n" + LOCAL + "/recover-password?tk=" + token);
        mailSender.send(message);
    }
}
