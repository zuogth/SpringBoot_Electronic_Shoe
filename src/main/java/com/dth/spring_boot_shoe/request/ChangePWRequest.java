package com.dth.spring_boot_shoe.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ChangePWRequest {

    @NotBlank(message = "Vui lòng nhập mật khẩu hiện tại của bạn")
    @Length(min = 6,message = "Mật khẩu phải ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập mật khẩu mới của bạn")
    @Length(min = 6,message = "Mật khẩu phải ít nhất 6 ký tự")
    private String password_new;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu mới của bạn")
    @Length(min = 6,message = "Mật khẩu phải ít nhất 6 ký tự")
    private String re_password_new;
}
