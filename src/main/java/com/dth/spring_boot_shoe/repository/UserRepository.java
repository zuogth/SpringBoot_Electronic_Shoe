package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmailAndStatusAndEnabled(String email,Integer status,Boolean enabled);
    Optional<UserEntity> findByEmailAndStatus(String email,Integer status);
    Optional<UserEntity> findByVerificationToken(String verificationToken);
    boolean existsByEmail(String email);

    Page<UserEntity> findByStatus(Integer status, Pageable pageable);

    @Query(value = "select count(*) from user_role where role_id=2",nativeQuery = true)
    List<Object[]> getCountUsers();
}
