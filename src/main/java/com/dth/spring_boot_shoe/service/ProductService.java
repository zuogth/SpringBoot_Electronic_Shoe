package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.request.ProductFilter;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.response.Product;
import com.dth.spring_boot_shoe.response.ProductDetail;
import com.dth.spring_boot_shoe.response.SizeQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductEntity> findByBrandId(Long brand_id);
    List<ProductDetailDTO> findByBrandIdGroupByProductIdAndColorId(Long brand_id);
    ProductDetailDTO findById(Long id);
    List<SizeQuantity> findAllSizeByProductId(Long id, Long color_id);
    List<ProductDetailDTO> findSameProduct(Long product_id);
    List<ProductDetailDTO> findBySlug(String slug);

    List<ProductEntity> findProductByBrandAndSize(ProductFilter productFilter);

    List<ProductDetailDTO> findByBrandAndOptions(ProductFilter productFilter);
    //Admin
    Map<String,Object> findAllByBrand(Long brand_id, int page);

    Map<String,Object> findAllDetailByProduct(Long product_id,int page);
    Product getById(Long id);

    void update(Product product);
    ProductEntity insert(Product product);

    void lockProd(Long id,Integer status);
}
