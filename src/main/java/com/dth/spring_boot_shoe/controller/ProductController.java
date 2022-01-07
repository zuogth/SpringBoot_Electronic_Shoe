package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.CommentEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.response.SizeQuantity;
import com.dth.spring_boot_shoe.service.CommentService;
import com.dth.spring_boot_shoe.service.ImageService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public String product(Model model, @PathVariable("id") Long id){
        ProductDetailDTO prod=productService.findById(id);
        List<SizeQuantity> sizes=productService.findAllSizeByProductId(prod.getProduct().getId(),prod.getColor().getId());
        List<ProductDetailDTO> sameProd=productService.findSameProduct(prod.getProduct().getId());
        List<String> images=imageService.findByColorIdAndProductId(prod.getColor().getId(),prod.getProduct().getId());
        List<CommentEntity> commentEntities=commentService.findByProductId(prod.getProduct().getId());
        model.addAttribute("prod",prod);
        model.addAttribute("sizes",sizes);
        model.addAttribute("sameProd",sameProd);
        model.addAttribute("images",images);
        model.addAttribute("comments",commentEntities);
        return "detail";
    }
}
