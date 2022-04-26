package com.dth.spring_boot_shoe.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "product_detail", indexes = {
        @Index(name = "fk_prod_size_idx", columnList = "size_id"),
        @Index(name = "fk_prod_feature_idx", columnList = "product_id"),
        @Index(name = "fk_prod_color_idx", columnList = "color_id")
})
@Entity
@Data
public class ProductDetailEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeEntity size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    private Integer status;
}