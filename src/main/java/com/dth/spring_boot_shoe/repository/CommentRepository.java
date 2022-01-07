package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

    Optional<CommentEntity> findByUserIdAndAndProductId(Long userId, Long productId);

    List<CommentEntity> findByProductId(Long productId);
}
