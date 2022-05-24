package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.entity.BrandEntity;
import com.dth.spring_boot_shoe.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelController {
    private final BrandRepository brandRepository;

    @ModelAttribute("menus")
    public List<BrandEntity> getMenus(){
        List<BrandEntity> entities = brandRepository.findAll();
        return entities;
    }
}
