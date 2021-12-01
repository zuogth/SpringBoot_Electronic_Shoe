package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BaseController {

    private final ModelMapper modelMapper;
    private final ProductService productService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }
}
