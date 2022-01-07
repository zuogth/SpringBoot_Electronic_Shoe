package com.dth.spring_boot_shoe.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    @NotBlank(message = "Hãy nhập tên của bạn")
    private String firstName;

    @NotBlank(message = "Hãy nhập họ của bạn")
    private String lastName;
    @NotBlank(message = "Hãy nhập số điện thoại của bạn")
    private String phone;

    private String address;
    @NotBlank(message = "Hãy chọn thành phố của bạn")
    private String province;

    @NotBlank(message = "Hãy chọn huyện/quận của bạn")
    private String district;

    @NotBlank(message = "Hãy chọn xã/phường của bạn")
    private String ward;

    @NotBlank(message = "Hãy điền chi tiết thêm địa chỉ của bạn")
    private String village;
    private String fullName;
    @NotNull(message = "Hãy nhập email của bạn")
    @Email(message = "Email không hợp lệ")
    private String email;

}
