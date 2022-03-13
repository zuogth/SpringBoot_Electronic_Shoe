package com.dth.spring_boot_shoe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @GetMapping({"/",""})
    public String index(){
        return "/admin/index";
    }

    @GetMapping("/product")
    public String products(){return "/admin/product";}
}
