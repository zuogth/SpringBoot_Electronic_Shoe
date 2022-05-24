package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.ProductDetail;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.DiscountEntity;
import com.dth.spring_boot_shoe.entity.EventEntity;
import com.dth.spring_boot_shoe.request.DiscountRequest;
import com.dth.spring_boot_shoe.request.EventRequest;
import com.dth.spring_boot_shoe.response.EventResponse;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;

import java.util.List;
import java.util.Map;

public interface EventService {

    Map<String,Object> getAll(int page);
    List<?> getProducts(String type,Long id);
    EventEntity insert(EventRequest discount);
    void update(EventRequest discount);

    EventResponse getById(Long id);

    List<ProductDetailResponse> findProductDetails(Long brandId,Long productId);

    void insertDis(DiscountRequest[] requests);

    List<ProductDetail> findProductSale(String slugEvent);

    List<ProductDetail> findAllProductsSale();
    void delete(Long id);

    void updateShow(Long id, Integer showWeb);
}
