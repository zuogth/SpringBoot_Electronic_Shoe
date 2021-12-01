package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.ProductBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBillRepository extends JpaRepository<ProductBillEntity,Long> {
    ProductBillEntity findByBillIdAndProductDetailId(Long billId,Long prodDetailId);

    List<ProductBillEntity> findByBillId(Long billId);
}
