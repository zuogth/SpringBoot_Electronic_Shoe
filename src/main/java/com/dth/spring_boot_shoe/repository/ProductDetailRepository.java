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
            "where pd.product.brand.slug=:brandSlug and pd.status <> -1 " +
            "group by pd.product.id,pd.color.id")
    List<ProductDetailEntity> findByBrandSlugGroupByProductIdAndColorId(@Param("brandSlug") String brandSlug);

    @Query("select p from ProductDetailEntity p where p.product.id=:product_id and p.status <> -1 group by p.product,p.color")
    List<ProductDetailEntity> findByProductIdGroupByProductIdAndColorId(@Param("product_id") Long product_id);

    @Query("select p from ProductDetailEntity p " +
            "where p.product.slug like %:slug% and p.status <> -1 " +
            "group by p.color.id")
    List<ProductDetailEntity> findBySlugProduct(@Param("slug") String slug);

    List<ProductDetailEntity> findByProductIdAndColorIdAndStatusNot(Long productId,Long colorId,Integer status);

    //sản phẩm mua nhiều
    @Query(value = "select pd.* from product_bill pb join product_detail pd on pb.product_detail_id=pd.id " +
            "group by pd.product_id,pd.color_id " +
            "order by sum(pb.quantity) desc " +
            "limit 7",nativeQuery = true)
    List<ProductDetailEntity> findTopBySumQuantity();

    //sản phẩm mới
    @Query(value = "select * from product_detail pd " +
            "group by pd.product_id,pd.color_id " +
            "order by pd.created_at desc " +
            "limit 7",nativeQuery = true)
    List<ProductDetailEntity> findTopByProductNew();

    //Admin

    @Query("select pd from ProductDetailEntity pd " +
            "where pd.product.id=:product_id and pd.status <> -1 " +
            "group by pd.color.id")
    Page<ProductDetailEntity> findByProductIdGroupByColor(@Param("product_id") Long product_id, Pageable pageable);

    List<ProductDetailEntity> findByProductIdAndStatusNot(Long productId,Integer status);

    Optional<ProductDetailEntity> findByIdAndStatusNot(Long id,Integer status);

    @Query(value = "select pd from ProductDetailEntity pd " +
            "where pd.product.slug=:productSlug and pd.color.slug=:color and pd.status <> -1 " +
            "group by pd.product.id")
    Optional<ProductDetailEntity> findByProductSlugAndColorAndStatusNot(@Param("productSlug") String productSlug,
                                                                        @Param("color") String color);

    Optional<ProductDetailEntity> findTopByProductIdAndColorIdAndStatusNot(Long productId,Long colorId,Integer status);
}
