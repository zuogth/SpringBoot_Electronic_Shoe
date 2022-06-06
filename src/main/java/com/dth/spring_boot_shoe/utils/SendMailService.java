package com.dth.spring_boot_shoe.utils;

import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.entity.BillEntity;
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
import java.util.List;

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

    public void sendMailOrder(UserEntity user, List<BillReceiptDTO> dtos, BillEntity bill,String subject,String htmlMsg, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String fromAddress = "youlookgood+shoe+dth";
        String senderName = "DTH Support";
        StringBuffer str=new StringBuffer();
        str.append("<h4 style='text-transform: uppercase;'>Thông tin đơn hàng</h4><table style='width: 50%'><tbody>");
        dtos.forEach(dto->str.append("<tr>" +
                "                <td style='width: 15%;'><img src='"+dto.getDetail().getImage()+"' alt='img' style='width:100%;'></td>" +
                "                <td style='padding: 1rem;font-weight: 700;'><p>"+dto.getDetail().getName()+" / "+dto.getDetail().getColor()+" x "+dto.getQuantity()+"</p><p>Cỡ: "+dto.getDetail().getSize()+"</p></td>" +
                "                <td style='padding: 1rem;text-align: end;font-weight: 700;'>"+StringUtils.toMoney(dto.getDetail().getPrice())+"đ</td>" +
                "            </tr>"));
        str.append("</tbody>" +
                "    </table>" +
                "    <table style='width: 50%'>" +
                "        <tr>" +
                "            <td style='width: 15%;'></td>" +
                "            <td style='padding: 1rem;'>" +
                "                <table style='width: 100%'>" +
                "                    <tbody>" +
                "                        <tr>" +
                "                            <td><p style='font-weight: 700;'>Tổng giá trị sản phẩm</p></td>" +
                "                            <td style='text-align: end;font-weight: 700;'><p>"+StringUtils.toMoney(bill.getTotalprice())+"đ</p></td>" +
                "                        </tr>" +
                "                        <tr>" +
                "                            <td><p style='font-weight: 700;'>Khuyến mãi</p></td>" +
                "                            <td style='text-align: end;font-weight: 700;'><p>0đ</p></td>" +
                "                        </tr>" +
                "                        <tr>" +
                "                            <td><p style='font-weight: 700;'>Phí vận chuyển</p></td>" +
                "                            <td style='text-align: end;font-weight: 700;'><p>0đ</p></td>" +
                "                        </tr>" +
                "                    </tbody>" +
                "                </table>" +
                "                <hr>" +
                "                <table style='width:100%'>" +
                "                    <tbody>" +
                "                        <tr>" +
                "                            <td><p style='font-weight: 700;'>Tổng cộng</p></td>" +
                "                            <td style='text-align: end;font-weight: 700;'><p>"+StringUtils.toMoney(bill.getTotalprice())+"đ</p></td>" +
                "                        </tr>" +
                "                    </tbody>" +
                "                </table>" +
                "            </td>" +
                "        </tr>" +
                "    </table>");
        htmlMsg+=str.toString();
        helper.setFrom(fromAddress,senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        String url = getUrl(request);
        String verifyURL = url + "/info/order";
        htmlMsg = htmlMsg.replace("[[URL]]",verifyURL);

        message.setContent(htmlMsg, "text/html; charset=utf-8");
        this.javaMailSender.send(message);

    }

    public void sendMailOrder2(UserEntity user, List<BillReceiptDTO> dtos, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String fromAddress = "youlookgood+shoe+dth";
        String senderName = "DTH Support";
        String htmlMsg = "<p><strong>Các sản phẩm trong đơn hàng của bạn đang được vận chuyển</strong></p><p>Các sản phẩm trong đơn hàng của bạn đang trên đường giao cho bạn. Theo dõi vận đơn để biết tình trạng giao hàng.</p><a href='[[URL]]' target='_self'>Xem đơn hàng</a><p>Cảm ơn !</p>";
        StringBuffer str=new StringBuffer();
        str.append("<h4>Các sản phẩm đang vận chuyển</h4><table style='width: 50%'><tbody>");
        dtos.forEach(dto->str.append("<tr>" +
                "                <td style='width: 15%;'><img src='"+dto.getDetail().getImage()+"' alt='img' style='width:100%;'></td>" +
                "                <td style='padding: 1rem;font-weight: 700;'><p>"+dto.getDetail().getName()+" / "+dto.getDetail().getColor()+" x "+dto.getQuantity()+"</p><p>Cỡ: "+dto.getDetail().getSize()+"</p></td>" +
                "            </tr>"));
        str.append("</tbody>" +
                "    </table>");
        htmlMsg+=str.toString();
        helper.setFrom(fromAddress,senderName);
        helper.setTo(user.getEmail());
        helper.setSubject("Xác nhận giao hàng cho đơn hàng!");

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
