package com.dth.spring_boot_shoe.dto;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDetail {
    private Long id;
    private String name;
    private String productSlug;
    private String colorSlug;
    private String color;
    private Integer size;
    private String image;
    private BigDecimal price;
    private Integer discount;
    private Boolean rate;

    public static ProductDetail converter(ProductDetailEntity entity,String image,Integer discount){
        return ProductDetail.builder().id(entity.getId()).name(entity.getProduct().getName()).productSlug(entity.getProduct().getSlug())
                .color(entity.getColor().getName()).colorSlug(entity.getColor().getSlug()).size(entity.getSize().getName()).image(image)
                .price(entity.getProduct().getPrice()).discount(discount).build();
    }
}
