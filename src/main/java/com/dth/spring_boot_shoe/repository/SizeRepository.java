package com.dth.spring_boot_shoe.repository;

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
}
