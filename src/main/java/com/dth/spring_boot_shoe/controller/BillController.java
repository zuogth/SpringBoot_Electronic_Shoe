package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.request.UserRequest;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final UserService userService;

    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/delivery")
    public String delivery(Model model){
        List<BillReceiptDTO> dtos= billService.updateToBill();
        UserRequest user=userService.loadUser();
        model.addAttribute("bills",dtos);
        model.addAttribute("user",user);
        return "delivery";
    }

    @PostMapping("/delivery")
    public String updateToBill(@Valid @ModelAttribute("user")UserRequest dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/delivery";
        }
        billService.updateToBill(dto);
        return "redirect:/";
    }
}
