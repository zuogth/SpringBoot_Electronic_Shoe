package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.request.ColorRequest;
import com.dth.spring_boot_shoe.response.BrandResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PropertiesService {

    Map<String,Object> getAll();
    Map<String,Object> getPage(int page,String flag);
    void colorUpdate(ColorRequest colorRequest);
    void sizeUpdate(Long id,Integer size);
    void brandUpdate(Long id, MultipartFile file);
    List<BrandResponse> getBrands();

    void colorInsert(ColorRequest colorRequest);
    void sizeInsert(Integer size);
}
