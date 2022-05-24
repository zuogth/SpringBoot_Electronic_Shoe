package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.request.ProductFilter;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.service.ProductService;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BaseAPI {

    private final ProductService productService;

    @PostMapping("/search")
    public List<ProductDetailDTO> search(@RequestBody ProductEntity prod) {
        String keySearch= StringUtils.removeAccent(prod.getSlug());
        return productService.findBySlug(keySearch);
    }

    @GetMapping("/brand")
    public List<ProductDetailDTO> brand() {
        return productService.findByBrandSlugGroupByProductIdAndColorId("adidas");
    }

    @PostMapping("/filter")
    public Map<String,Object> filter(@RequestBody ProductFilter prod) {
        HashMap<String,Object> map=new HashMap<>();
        List<ProductDetailDTO> detailEntities;
        List<ProductEntity> entities;
        detailEntities=productService.findByBrandAndOptions(prod);
        if(prod.getColorId()==-1){
            entities=productService.findProductByBrandAndSize(prod);
            map.put("products",entities);
        }
        map.put("details",detailEntities);
        return map;
    }

}
