package com.dth.spring_boot_shoe.entity;

import com.dth.spring_boot_shoe.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "discount")
@Entity
@Data
public class DiscountEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Column(name = "description")
    private String description;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @Column(name = "brand_id")
    private Long brandId;
}