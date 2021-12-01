package com.dth.spring_boot_shoe.entity;

import com.dth.spring_boot_shoe.entity.BaseEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.ReceiptEntity;
import lombok.Data;

import javax.persistence.*;

@Table(name = "product_receipt", indexes = {
        @Index(name = "fk_prod_receipt_idx", columnList = "product_detail_id"),
        @Index(name = "fk_receipt_prod_idx", columnList = "receipt_id")
})
@Entity
@Data
public class ProductReceiptEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private ReceiptEntity receipt;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity productDetail;

    @Column(name = "quantity")
    private Integer quantity;
}