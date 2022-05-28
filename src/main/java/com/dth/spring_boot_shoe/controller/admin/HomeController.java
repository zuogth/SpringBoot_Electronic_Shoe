package com.dth.spring_boot_shoe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @GetMapping({"/",""})
    public String index(){
        return "admin/index";
    }

    @GetMapping("/products")
    public String products(){return "admin/product";}

    @GetMapping("/receipts")
    public String receipts(){return "admin/receipt";}

    @GetMapping("/bills")
    public String bills(@RequestParam(value = "now",required = false) boolean now, Model model){
        model.addAttribute("now", now);
        return "admin/bill";
    }

    @GetMapping("/properties")
    public String properties(){return "admin/properties";}

    @GetMapping("/users")
    public String users(){return "admin/user";}

    @GetMapping("/events")
    public String discounts(){return "admin/event";}
}
