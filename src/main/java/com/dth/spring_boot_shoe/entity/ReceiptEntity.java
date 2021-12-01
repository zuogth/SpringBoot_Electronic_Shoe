package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "receipt", indexes = {
        @Index(name = "fk_user_receipt_idx", columnList = "user_id")
})
@Entity
@Data
public class ReceiptEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "totalprice", precision = 18, scale = 2)
    private BigDecimal totalprice;
}