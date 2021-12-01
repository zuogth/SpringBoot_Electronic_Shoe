package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.request.ProductFilter;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> findByBrandId(Long brand_id);
    List<ProductDetailDTO> findByBrandIdGroupByProductIdAndColorId(Long brand_id);
    ProductDetailDTO findById(Long id);
    List<ProductDetailEntity> findAllSizeByProductId(Long id,Long color_id);
    List<ProductDetailDTO> findSameProduct(Long product_id);
    List<ProductDetailDTO> findBySlug(String slug);


    List<ProductEntity> findProductByBrandAndSize(ProductFilter productFilter);

    List<ProductDetailDTO> findByBrandAndOptions(ProductFilter productFilter);

}
