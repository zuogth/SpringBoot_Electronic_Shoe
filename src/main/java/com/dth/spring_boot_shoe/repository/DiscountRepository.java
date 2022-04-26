package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountEntity,Long> {
}
