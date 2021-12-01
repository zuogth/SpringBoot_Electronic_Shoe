package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "bill", indexes = {
        @Index(name = "fk_user_bill_idx", columnList = "user_id")
})
@Entity
@Data
public class BillEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "totalprice", precision = 18, scale = 2)
    private BigDecimal totalprice;

    @Column(name = "bill_type")
    private Integer billType;

    @Column(name = "status", length = 10)
    private String status;
}