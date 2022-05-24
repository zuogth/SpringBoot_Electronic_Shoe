package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ReceiptEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ReceiptResponse {
    private Long id;
    private String userName;
    private String brandName;
    private Long brandId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;

    public static ReceiptResponse converter(ReceiptEntity entity,Integer quantity){
        return ReceiptResponse.builder().id(entity.getId()).brandId(entity.getBrand().getId())
                .userName(entity.getUser().getFullName()).brandName(entity.getBrand().getName())
                .quantity(quantity).totalPrice(entity.getTotalprice()).createdDate(entity.getCreatedAt()).build();
    }
}
