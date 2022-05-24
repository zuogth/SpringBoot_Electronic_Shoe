package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity,Long> {

    BrandEntity findBySlug(String slug);
    @Query(value = "select max(p.price) from ProductEntity p")
    BigDecimal getMaxPrice();
}
