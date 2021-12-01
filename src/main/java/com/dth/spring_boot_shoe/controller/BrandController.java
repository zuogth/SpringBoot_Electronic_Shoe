package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.*;
import com.dth.spring_boot_shoe.repository.BrandRepository;
import com.dth.spring_boot_shoe.repository.ColorRepository;
import com.dth.spring_boot_shoe.repository.SizeRepository;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final ProductService productService;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;

    @GetMapping("/{brand_id}")
    public String shop(Model model, @PathVariable("brand_id") Long brand_id){
        List<ProductEntity> products=productService.findByBrandId(brand_id);
        List<ProductDetailDTO> details=productService.findByBrandIdGroupByProductIdAndColorId(brand_id);
        List<SizeEntity> sizes=sizeRepository.findSizeByProduct(brand_id);
        List<ColorEntity> colors=colorRepository.findColorByProduct(brand_id);
        List<BrandEntity> brands=brandRepository.findAll();
        model.addAttribute("products",products);
        model.addAttribute("details",details);
        model.addAttribute("sizes",sizes);
        model.addAttribute("colors",colors);
        model.addAttribute("brands",brands);
        return "brand";
    }
}
