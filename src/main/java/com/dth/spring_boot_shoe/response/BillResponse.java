package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.BillEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BillResponse {
    private Long id;
    private String userName;
    private BigDecimal totalPrice;
    private String status;
    private String address;
    private LocalDateTime createdDate;

    public static BillResponse converter(BillEntity entity){
        return BillResponse.builder().id(entity.getId()).userName(entity.getUser().getFullName())
                .totalPrice(entity.getTotalprice()).status(entity.getStatus())
                .address(entity.getAddress()).createdDate(entity.getCreatedAt()).build();
    }
}
