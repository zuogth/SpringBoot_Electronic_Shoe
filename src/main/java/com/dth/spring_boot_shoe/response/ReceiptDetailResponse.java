package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ProductReceiptEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptDetailResponse {
    private Long id;
    private Long productDetailId;
    private String productName;
    private String image;
    private String color;
    private Integer size;
    private Integer receipt;

    public static ReceiptDetailResponse converter(ProductReceiptEntity entity, String image, Integer receipt){
        return ReceiptDetailResponse.builder().id(entity.getId()).productDetailId(entity.getProductDetail().getId())
                .productName(entity.getProductDetail().getProduct().getName()).image(image)
                .receipt(receipt).color(entity.getProductDetail().getColor().getName())
                .size(entity.getProductDetail().getSize().getName()).build();
    }
}
