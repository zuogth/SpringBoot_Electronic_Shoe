package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductDetail;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.EventEntity;
import com.dth.spring_boot_shoe.exception.RequestException;
import com.dth.spring_boot_shoe.repository.EventRepository;
import com.dth.spring_boot_shoe.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class DiscountController {

    private final EventService eventService;
    private final EventRepository eventRepository;

    @GetMapping("/{slug}")
    public String getBySlug(Model model, @PathVariable("slug") String slug){
        EventEntity event = eventRepository.findBySlug(slug).orElseThrow(()->new RequestException("Sự kiện đang cập nhật!","/"));
        List<ProductDetail> detailDTOS = eventService.findProductSale(slug);
        model.addAttribute("products",detailDTOS);
        model.addAttribute("event",event);
        return "event";
    }

    @GetMapping("/promotion/all")
    public String getAll(Model model){
        EventEntity event =new EventEntity();
        event.setUrl("/assets/img/Clearance-Sale-Desktop.jpg");

        List<ProductDetail> details = eventService.findAllProductsSale();
        model.addAttribute("event",event);
        model.addAttribute("products",details);
        return "event";
    }
}
