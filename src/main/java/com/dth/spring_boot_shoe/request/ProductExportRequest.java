package com.dth.spring_boot_shoe.request;

import com.dth.spring_boot_shoe.entity.ProductReceiptEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Hiu
 * Date: 5/24/2022
 */
@Data
@Builder
public class ProductExportRequest {
    private Integer no;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public static ProductExportRequest converter(Integer index, ProductReceiptEntity entity, Integer quantity){
        BigDecimal q = new BigDecimal(quantity);
        return ProductExportRequest.builder().no(index).productName(entity.getProductDetail().getProduct().getName())
                .price(entity.getProductDetail().getProduct().getPrice()).quantity(quantity).totalPrice(entity.getProductDetail().getProduct().getPrice().multiply(q)).build();
    }
}
