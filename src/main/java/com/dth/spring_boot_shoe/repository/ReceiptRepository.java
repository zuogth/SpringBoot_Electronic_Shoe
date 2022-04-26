package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity,Long> {
}
