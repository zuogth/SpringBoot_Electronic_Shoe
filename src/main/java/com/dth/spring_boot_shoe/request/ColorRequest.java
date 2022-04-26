package com.dth.spring_boot_shoe.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ColorRequest {

    private Long id;

    @NotBlank(message ="Bạn chưa nhập tên màu")
    private String name;

    @NotBlank(message = "Bạn chưa chọn màu hiển thị")
    private String code;
}
