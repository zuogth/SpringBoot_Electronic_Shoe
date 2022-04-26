package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.request.ProductDetail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductDetailService {

    Long insert(ProductDetail productDetail);
    void insertSize(ProductDetail productDetail,Long sizeId);
    Map<String,Object> findById(Long productId,Long colorId);

    void update(ProductDetail productDetail) throws IOException;

    void updateSizeForProductDetail(Long id,Long sizeId);

    void lock(Long id,Integer status);

    void delete(Long id);

    ApiException deleteSingle(Long id);
}
