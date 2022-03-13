package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity,Long> {

    @Query("select p.color from ProductDetailEntity p where p.product.brand.id=:brand_id group by p.color.id")
    List<ColorEntity> findColorByProduct(@Param("brand_id") Long brand_id);

    //get color to insert: colors not used yet
    @Query("select c from ColorEntity c " +
            "where c.id not in (select p.color.id from ProductDetailEntity p " +
            "where p.product.id=:productId group by p.color.id)")
    List<ColorEntity> findColorByNotInProductDetail(@Param("productId") Long productId);

    //get color to update: colors not used yet and it
    @Query("select p.color from ProductDetailEntity p " +
            "where p.color.id not in (select p.color.id from ProductDetailEntity p " +
            "where p.product.id=:productId group by p.color.id) or p.id=:id " +
            "group by p.color.id")
    List<ColorEntity> findColorByNotInProductDetailAndIt(@Param("id") Long id,
                                                         @Param("productId") Long productId);
}
