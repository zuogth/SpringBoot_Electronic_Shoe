package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity,Long> {
    BillEntity findByUserId(Long userId);
}
