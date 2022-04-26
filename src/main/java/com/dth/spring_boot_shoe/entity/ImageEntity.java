package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "images")
@Entity
@Data
public class ImageEntity extends BaseEntity{
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "color_id", nullable = false)
    private Long colorId;

    @Column(name = "image",length = 45)
    private String image;

    @Column(name = "url")
    private String url;

    @Column(name = "parent")
    private Integer parent;
}