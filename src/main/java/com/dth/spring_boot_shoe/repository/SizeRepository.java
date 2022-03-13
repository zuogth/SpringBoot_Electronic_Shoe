package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ColorEntity;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeRepository extends JpaRepository<SizeEntity,Long> {
    @Query("select p.size from ProductDetailEntity p where p.product.brand.id=:brand_id " +
            "group by p.size.id " +
            "order by p.size.id asc")
    List<SizeEntity> findSizeByProduct(@Param("brand_id") Long brand_id);

    @Query("select s from SizeEntity s " +
            "where s.id not in (select p.size.id from ProductDetailEntity p " +
            "where p.product.id=:productId and p.color.id=:colorId)")
    List<SizeEntity> findSizeNotInProductDetail(@Param("productId") Long productId,
                                                 @Param("colorId") Long colorId);
}
