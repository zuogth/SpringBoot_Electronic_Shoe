package com.dth.spring_boot_shoe.utils;

import com.dth.spring_boot_shoe.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

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

    public void verifyCode(UserEntity user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String fromAddress = "youlookgood+shoe+dth";
        String senderName = "DTH Support";
        String htmlMsg = "<p><strong>Cảm ơn bạn đã đăng ký tài khoản tại trang web của chúng tôi.</strong></p><p>Vui lòng click <a href='[[URL]]' target='_self'>vào đây</a> để hoàn thành đăng ký!</p><p>Cảm ơn !</p>";

        helper.setFrom(fromAddress,senderName);
        helper.setTo(user.getEmail());
        helper.setSubject("Xác nhận đăng ký!");

        String url = getUrl(request);
        String verifyURL = url + "/verify?code="+user.getVerificationToken();
        htmlMsg = htmlMsg.replace("[[URL]]",verifyURL);

        message.setContent(htmlMsg, "text/html; charset=utf-8");
        this.javaMailSender.send(message);

    }

    public void sendMailOrder(UserEntity user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String fromAddress = "youlookgood+shoe+dth";
        String senderName = "DTH Support";
        String htmlMsg = "<p><strong>Cám ơn bạn đã mua hàng!</strong></p><p>Bên mình đã nhận được đơn hàng của bạn, sẽ giao sản phẩm đến với bạn sớm nhất.</p><a href='[[URL]]' target='_self'>Xem đơn hàng</a><p>Cảm ơn !</p>";

        helper.setFrom(fromAddress,senderName);
        helper.setTo(user.getEmail());
        helper.setSubject("Đơn đặt hàng");

        String url = getUrl(request);
        String verifyURL = url + "/info/order";
        htmlMsg = htmlMsg.replace("[[URL]]",verifyURL);

        message.setContent(htmlMsg, "text/html; charset=utf-8");
        this.javaMailSender.send(message);

    }

    private String getUrl(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(),"");
    }
}
