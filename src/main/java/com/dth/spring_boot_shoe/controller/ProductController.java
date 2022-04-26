package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.dto.ProductListDTO;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final CommentService commentService;

    @GetMapping("/{slug}/{color}")
    public String product(Model model, @PathVariable("slug") String slug,@PathVariable("color") String color){
        ProductDetailDTO prod=productService.findBySlugAndColor(slug,color);
        List<SizeQuantity> sizes=productService.findAllSizeByProductId(prod.getProduct().getId(),prod.getColor().getId());
        List<ProductDetailDTO> sameProd=productService.findSameProduct(prod.getProduct().getId());
        List<String> images=imageService.findByColorIdAndProductId(prod.getColor().getId(),prod.getProduct().getId());
        List<CommentEntity> commentEntities=commentService.findByProductId(prod.getProduct().getId());
        List<ProductListDTO> buyALot=productService.findTopBuy();
        model.addAttribute("prod",prod);
        model.addAttribute("sizes",sizes);
        model.addAttribute("sameProd",sameProd);
        model.addAttribute("images",images);
        model.addAttribute("comments",commentEntities);
        model.addAttribute("countStar",commentEntities.size());
        model.addAttribute("buyALot",buyALot);
        double avg=commentEntities.stream().mapToDouble(CommentEntity::getStar).sum()/commentEntities.size();
        model.addAttribute("avgStar",(double)Math.round(avg*10)/10);
        return "detail";
    }
}
