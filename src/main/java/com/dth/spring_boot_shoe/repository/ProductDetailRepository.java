package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity,Long> {

    @Query("select pd from ProductDetailEntity pd " +
            "where pd.product.brand.id=:brand_id " +
            "group by pd.product.id,pd.color.id")
    List<ProductDetailEntity> findByBrandIdGroupByProductIdAndColorId(@Param("brand_id") Long brand_id);

    @Query("select p from ProductDetailEntity p where p.product.id=:id and p.color.id=:color group by p.size " +
            "order by p.size.id asc")
    List<ProductDetailEntity> findByProductIdGroupBySizeId(@Param("id") Long id,
                                                  @Param("color") Long color);

    @Query("select p from ProductDetailEntity p where p.product.id=:product_id group by p.product,p.color")
    List<ProductDetailEntity> findByProductIdGroupByProductIdAndColorId(@Param("product_id") Long product_id);

    @Query("select p from ProductDetailEntity p " +
            "where p.product.slug like %:slug% " +
            "group by p.color.id")
    List<ProductDetailEntity> findBySlugProduct(@Param("slug") String slug);

}
