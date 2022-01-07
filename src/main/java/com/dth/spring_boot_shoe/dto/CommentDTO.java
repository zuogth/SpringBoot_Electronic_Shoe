package com.dth.spring_boot_shoe.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDTO extends BaseDTO{

    private Long userId;
    private Long productId;
    @NotBlank(message = "Tiêu đề đánh giá không thể bỏ trống")
    private String title;

    @NotBlank(message = "Nội dung đánh giá không thể bỏ trống")
    @Length(min = 30,message = "Nội dung đánh giá ít nhất 30 ký tự")
    private String content;

    @NotNull(message = "Vui lòng chọn số sao đánh giá")
    private Integer star;
}
