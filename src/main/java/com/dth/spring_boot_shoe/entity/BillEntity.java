package com.dth.spring_boot_shoe.entity;

import com.dth.spring_boot_shoe.constant.StatusBill;
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

    @Column(name = "paying")
    private Integer paying;

    @Column(name = "status", length = 15)
    private String status;

    @Column(name = "payment", length = 4)
    private String payment;

    @Column(name = "address", length = 100)
    private String address;
}