package com.dth.spring_boot_shoe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Table(name = "products", indexes = {
        @Index(name = "fk_prod_brand_idx", columnList = "brand_id")
})
@Entity
@Data
public class ProductEntity extends BaseEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "slug", length = 45)
    private String slug;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status;
}