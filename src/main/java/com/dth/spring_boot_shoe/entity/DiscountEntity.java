package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;
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

}