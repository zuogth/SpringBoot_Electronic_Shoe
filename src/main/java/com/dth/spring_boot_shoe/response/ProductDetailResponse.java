package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDetailResponse {

    private Long id;
    private Long productId;
    private String name;
    private String image;
    private Integer sale;
    private String color;
    private Long colorId;
    private Integer size;
    private Long sizeId;
    private Integer receipt;
    private Integer sold;
    private List<String> images;
    private Integer status;
    private BigDecimal price;

    public static ProductDetailResponse converter(ProductDetailEntity entity,
                                                  Integer sold, Integer receipt,
                                                  String image, List<String> images){
        return ProductDetailResponse.builder()
                .id(entity.getId()).receipt(receipt).images(images).name(entity.getProduct().getName())
                .color(entity.getColor().getName()).colorId(entity.getColor().getId()).image(image)
                .size(entity.getSize().getName()).sizeId(entity.getSize().getId()).price(entity.getProduct().getPrice())
                .sold(sold).sale(0).productId(entity.getProduct().getId()).status(entity.getStatus()).build();
    }
}
