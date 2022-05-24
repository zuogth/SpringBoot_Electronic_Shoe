package com.dth.spring_boot_shoe.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class EventRequest {
    @NotBlank(message = "Tên sự kiện không được bỏ trống")
    private String name;
    private String description;
    @NotBlank(message = "Bạn chưa chọn thời gian bắt đầu")
    private String startAt;
    @NotBlank(message = "Bạn chưa chọn thời gian kết thúc")
    private String endAt;
    @NotNull(message = "Bạn chưa chọn style hiển thị")
    private String style;
    private MultipartFile banner;
    private Integer show;
    private List<Integer> discounts;

}
