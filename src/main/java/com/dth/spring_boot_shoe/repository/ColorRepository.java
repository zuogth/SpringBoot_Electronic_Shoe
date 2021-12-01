package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColorRepository extends JpaRepository<ColorEntity,Long> {
    @Query("select p.color from ProductDetailEntity p where p.product.brand.id=:brand_id group by p.color.id")
    List<ColorEntity> findColorByProduct(@Param("brand_id") Long brand_id);
}
