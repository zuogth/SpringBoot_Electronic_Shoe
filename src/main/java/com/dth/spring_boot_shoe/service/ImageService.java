package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.entity.ImageEntity;

import java.util.List;

public interface ImageService {
    List<String> findByColorIdAndProductId(Long color_id,Long product_id);
    String findByColorIdAndProductIdAndParent(Long color_id,Long product_id);
}
