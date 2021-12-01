package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {
    List<ImageEntity> findByColorIdAndProductId(Long color_id,Long product_id);
    Optional<ImageEntity> findByColorIdAndProductIdAndParent(Long color_id, Long product_id, Integer parent);
}
