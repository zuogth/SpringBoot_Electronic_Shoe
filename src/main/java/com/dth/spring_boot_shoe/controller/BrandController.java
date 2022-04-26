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
@RequestMapping("/products")
public class BrandController {
    private final ProductService productService;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;

    @GetMapping("/{brandSlug}")
    public String shop(Model model, @PathVariable("brandSlug") String brandSlug){
        List<ProductEntity> products=productService.findByBrandSlug(brandSlug);
        List<ProductDetailDTO> details=productService.findByBrandSlugGroupByProductIdAndColorId(brandSlug);
        List<SizeEntity> sizes=sizeRepository.findSizeByProduct(brandSlug);
        List<ColorEntity> colors=colorRepository.findColorByProduct(brandSlug);
        List<BrandEntity> brands=brandRepository.findAll();
        model.addAttribute("products",products);
        model.addAttribute("details",details);
        model.addAttribute("sizes",sizes);
        model.addAttribute("colors",colors);
        model.addAttribute("brands",brands);
        model.addAttribute("brand",brandRepository.findBySlug(brandSlug));
        return "brand";
    }
}
