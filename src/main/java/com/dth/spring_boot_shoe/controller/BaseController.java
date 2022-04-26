package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductListDTO;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BaseController {

    private final ProductService productService;
    @GetMapping("/")
    public String index(Model model){
        List<ProductListDTO> buyALot=productService.findTopBuy();
        List<ProductListDTO> productsNew=productService.findTopProductNew();
        model.addAttribute("buyALot",buyALot);
        model.addAttribute("productsNew",productsNew);
        return "index";
    }

}
