package com.dth.spring_boot_shoe.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@Data
public class ProductDetail {
    private Long id;

    private Long productId;

    @NotNull(message = "Bạn chưa chọn ảnh hiển thị cho sản phẩm")
    private MultipartFile file;

    @NotNull(message = "Bạn chưa chọn các ảnh xem chi tiết cho sản phẩm")
    private List<MultipartFile> fileChr;

    @NotNull(message = "Bạn chưa chọn màu")
    private Long colorId;

    @NotNull(message = "Bạn chưa chọn cỡ cho sản phẩm")
    private List<Long> sizeIds;
}
