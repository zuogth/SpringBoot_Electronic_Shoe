package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByCode(String code);
}
