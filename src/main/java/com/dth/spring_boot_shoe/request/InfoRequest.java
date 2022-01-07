package com.dth.spring_boot_shoe.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InfoRequest {
    @NotBlank(message = "Hãy nhập tên của bạn")
    private String firstName;

    @NotBlank(message = "Hãy nhập họ của bạn")
    private String lastName;
    @NotBlank(message = "Hãy nhập số điện thoại của bạn")
    private String phone;
    @NotNull(message = "Bạn chưa chọn giới tính")
    private Integer gender;
}
