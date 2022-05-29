package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import lombok.Builder;
import lombok.Data;


/**
 * Created by IntelliJ IDEA.
 * User: Hiu
 * Date: 5/29/2022
 */
@Data
@Builder
public class ProductDiscountResponse {
    private Long id;
    private String name;
    private String image;
    private Integer discount;
    private String colorCode;
    private Integer size;

    public static ProductDiscountResponse converter(ProductDetailEntity entity,String image,Integer discount){
        return ProductDiscountResponse.builder().id(entity.getDiscountId()).name(entity.getProduct().getName())
                .discount(discount).image(image).colorCode(entity.getColor().getCode()).size(entity.getSize().getName()).build();
    }
}
