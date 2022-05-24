package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<DiscountEntity,Long> {
    List<DiscountEntity> findByEventId(Long eventId);

}
