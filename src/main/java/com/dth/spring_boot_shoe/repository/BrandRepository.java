package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity,Long> {
}
