package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "receipt", indexes = {
        @Index(name = "fk_user_receipt_idx", columnList = "user_id"),
        @Index(name = "fk_brand_receipt_idx", columnList = "brand_id")
})
@Entity
@Data
public class ReceiptEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(name = "totalprice", precision = 18, scale = 2)
    private BigDecimal totalprice;
}