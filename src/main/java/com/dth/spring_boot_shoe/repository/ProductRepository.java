package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByIdAndStatusNot(Long id,Integer status);
    Page<ProductEntity> findByStatusNot(Integer status, Pageable pageable);
    Page<ProductEntity> findAllByBrandIdAndStatusNot(Long brand_id,Integer status, Pageable pageable);
    @Query("select p from ProductEntity p join ProductDetailEntity pd on p.id=pd.product.id " +
            "where p.brand.id=:brand_id and pd.color.id=:color_id and pd.size.id=:size_id " +
            "group by p.id")
    List<ProductEntity> findByBrandIdAndColorAndSize();


    //tìm product theo size
    @Query("select pd.product from ProductDetailEntity pd " +
            "where pd.product.brand.id=:brand_id and pd.size.id=:size_id and pd.status <> -1 " +
            "group by pd.product.id")
    List<ProductEntity> findProductByBrandAndSize(@Param("brand_id") Long brand_id,
                                                  @Param("size_id") Long size_id);

}
