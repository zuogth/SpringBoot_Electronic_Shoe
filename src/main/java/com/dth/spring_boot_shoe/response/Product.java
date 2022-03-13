package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;

    @NotBlank(message = "Tên sản phẩm không được bỏ trống")
    private String name;
    private String description;
    private String content;
    private Integer status;

    @NotNull(message = "Giá sản phẩm không được bỏ trống")
    @Min(value = 100000,message = "Giá sản phẩm không thể nhỏ hơn 100.000 VNĐ")
    private BigDecimal price;

    @NotNull(message = "Loại giày không thể bỏ trống")
    private String brand;
    private LocalDate createdDate;
    private Integer receipt;
    private Integer sold;
    private List<ProductDetail> detailList;

    public static Product converter(ProductEntity entity,List<ProductDetail> detailList,Integer sold,Integer receipt){
        return Product.builder().id(entity.getId()).createdDate(entity.getCreatedAt())
                .name(entity.getName()).brand(entity.getBrand().getId().toString())
                .description(entity.getDescription()).content(entity.getContent())
                .price(entity.getPrice()).status(entity.getStatus())
                .receipt(receipt).sold(sold).detailList(detailList).build();
    }
}
