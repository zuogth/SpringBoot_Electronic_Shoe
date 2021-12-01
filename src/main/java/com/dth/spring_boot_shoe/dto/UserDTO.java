package com.dth.spring_boot_shoe.dto;

import com.dth.spring_boot_shoe.security.provider.Provider;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO extends BaseDTO{

    @NotNull(message = "Hãy nhập email của bạn")
    private String email;
    @NotBlank(message = "Hãy nhập tên của bạn")
    private String firstName;
    @NotBlank(message = "Hãy nhập họ của bạn")
    private String lastName;
    @NotNull(message = "Bạn chưa chọn giới tính")
    private Integer gender;
    private String phone;
    @NotEmpty(message = "Bạn chưa nhập mật khẩu")
    private String password;
    private String address;
    private Integer status;
    private String fullName;


    public UserDTO(String email, String firstName, String lastName, Integer gender, String phone, String password, String address, Integer status, String fullName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.status = 1;
        this.fullName = lastName+" "+firstName;
    }
}
