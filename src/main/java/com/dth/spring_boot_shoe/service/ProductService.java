package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.ProductListDTO;
import com.dth.spring_boot_shoe.request.ProductFilter;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.response.ChartResponse;
import com.dth.spring_boot_shoe.response.Product;
import com.dth.spring_boot_shoe.response.SizeQuantity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductEntity> findByBrandSlug(String brandSlug);
    List<ProductDetailDTO> findByBrandSlugGroupByProductIdAndColorId(String brandSlug);
    ProductDetailDTO findBySlugAndColor(String productSlug,String colorSlug);
    ProductDetailDTO findByIdAndColor(Long productId,String colorSlug);
    List<SizeQuantity> findAllSizeByProductId(Long id, Long color_id);
    List<ProductDetailDTO> findSameProduct(Long product_id);
    List<ProductDetailDTO> findBySlug(String slug);

    List<ProductEntity> findProductByBrandAndSize(ProductFilter productFilter);

    List<ProductDetailDTO> findByBrandAndOptions(ProductFilter productFilter);
    //Admin
    Map<String,Object> findAllByBrand(String brandSlug, int page);

    Map<String,Object> findAllDetailByProduct(Long product_id,int page);
    Product getById(Long id);

    void update(Product product);
    ProductEntity insert(Product product);

    void lockProd(Long id,Integer status);
    void delete(Long id);

    List<ProductListDTO> findTopBuy();
    List<ProductListDTO> findTopProductNew();

    //Home admin
    Map<String,Object> getChart(int year);

    Map<String,Object> getAll();
}
