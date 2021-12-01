package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmailAndStatus(String email,Integer status);
    boolean existsByEmail(String email);
}
