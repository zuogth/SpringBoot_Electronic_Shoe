package com.dth.spring_boot_shoe.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SendMailService {

    private final JavaMailSender javaMailSender;

    public void sendText(String mailTo, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setSubject(subject);
        message.setText(content);

        // Send Message!
        this.javaMailSender.send(message);
    }

    public void sendHtml(String mailTo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = "<p><strong>Cảm ơn bạn đã đăng ký tài khoản tại trang web của chúng tôi.</strong></p><p>Vui lòng click <a href='http://localhost:8080/'>vào đây</a> để hoàn thành đăng ký!</p><p>Cảm ơn !</p>";

        message.setContent(htmlMsg, "text/html; charset=utf-8");

        helper.setTo(mailTo);

        helper.setSubject("Xác nhận đăng ký!");

        this.javaMailSender.send(message);

    }
}
