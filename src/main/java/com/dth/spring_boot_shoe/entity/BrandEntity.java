package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "brand")
@Entity
@Data
public class BrandEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "slug", length = 45)
    private String slug;

    @Column(name = "banner", length = 45)
    private String banner;

    @Column(name = "url")
    private String url;
}