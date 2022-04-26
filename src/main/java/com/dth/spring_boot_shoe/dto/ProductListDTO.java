package com.dth.spring_boot_shoe.dto;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductListDTO {

    private String name;
    private String slug;
    private String color;
    private BigDecimal price;
    private double avg;
    private String image;

    public static ProductListDTO converter(ProductDetailEntity entity,String image,double avg){
        return ProductListDTO.builder().name(entity.getProduct().getName()).slug(entity.getProduct().getSlug())
                .color(entity.getColor().getSlug()).price(entity.getProduct().getPrice()).image(image).avg(avg).build();
    }
}
