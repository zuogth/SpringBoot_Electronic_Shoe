package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "brand")
@Entity
@Data
public class BrandEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "slug", length = 45)
    private String slug;
}