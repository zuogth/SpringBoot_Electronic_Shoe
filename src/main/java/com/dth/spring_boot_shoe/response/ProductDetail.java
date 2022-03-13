package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDetail {

    private Long id;
    private Long productId;
    private String image;
    private Integer sale;
    private String color;
    private Long colorId;
    private Integer receipt;
    private Integer sold;
    private List<String> images;
    private Integer status;

    public static ProductDetail converter(ProductDetailEntity entity,
                                          Integer sold,Integer receipt,
                                          String image,List<String> images){
        return ProductDetail.builder()
                .id(entity.getId()).receipt(receipt).images(images)
                .color(entity.getColor().getName()).colorId(entity.getColor().getId()).image(image)
                .sold(sold).sale(0).productId(entity.getProduct().getId()).status(entity.getStatus()).build();
    }
}
