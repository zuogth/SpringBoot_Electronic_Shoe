package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity,Long> {

    @Query("select pd from ProductDetailEntity pd " +
            "where pd.product.brand.id=:brand_id and pd.status <> -1 " +
            "group by pd.product.id,pd.color.id")
    List<ProductDetailEntity> findByBrandIdGroupByProductIdAndColorId(@Param("brand_id") Long brand_id);

    @Query("select p from ProductDetailEntity p where p.product.id=:product_id and p.status <> -1 group by p.product,p.color")
    List<ProductDetailEntity> findByProductIdGroupByProductIdAndColorId(@Param("product_id") Long product_id);

    @Query("select p from ProductDetailEntity p " +
            "where p.product.slug like %:slug% and p.status <> -1 " +
            "group by p.color.id")
    List<ProductDetailEntity> findBySlugProduct(@Param("slug") String slug);

    List<ProductDetailEntity> findByProductIdAndColorIdAndStatusNot(Long productId,Long colorId,Integer status);

    //Admin

    @Query("select pd from ProductDetailEntity pd " +
            "where pd.product.id=:product_id and pd.status <> -1 " +
            "group by pd.color.id")
    Page<ProductDetailEntity> findByProductIdGroupByColor(@Param("product_id") Long product_id, Pageable pageable);

    List<ProductDetailEntity> findByProductIdAndStatusNot(Long productId,Integer status);

    Optional<ProductDetailEntity> findByIdAndStatusNot(Long id,Integer status);

}
