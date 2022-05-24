package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductListDTO;
import com.dth.spring_boot_shoe.entity.EventEntity;
import com.dth.spring_boot_shoe.repository.EventRepository;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class BaseController {

    private final ProductService productService;
    private final EventRepository eventRepository;

    @GetMapping("/")
    public String index(Model model){
        List<EventEntity> events = eventRepository.findByShowWebAndEndAtAfter(1, LocalDateTime.now());
        List<ProductListDTO> buyALot=productService.findTopBuy();
        List<ProductListDTO> productsNew=productService.findTopProductNew();
        model.addAttribute("buyALot",buyALot);
        model.addAttribute("productsNew",productsNew);
        model.addAttribute("events",events);
        return "index";
    }

    @GetMapping("/promotion")
    public String promotion(Model model){
        return "event";
    }
}
