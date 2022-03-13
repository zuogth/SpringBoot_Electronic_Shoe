package com.dth.spring_boot_shoe.entity;

import com.dth.spring_boot_shoe.entity.BaseEntity;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "product_bill", indexes = {
        @Index(name = "fk_bill_prod_idx", columnList = "bill_id"),
        @Index(name = "fk_prod_bill_idx", columnList = "product_detail_id")
})
@Entity
@Data
public class ProductBillEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity productDetail;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private BillEntity bill;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;
}