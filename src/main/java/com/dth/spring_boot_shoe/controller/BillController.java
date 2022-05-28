package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetail;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.request.UserRequest;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/payment")
    public String payment(Model model, @RequestParam(required = false,name = "payErr") Boolean payErr){
        BillEntity bill = billService.findByUserId();
        List<BillReceiptDTO> dtos= billService.updateToBill();

        model.addAttribute("bills",dtos);
        model.addAttribute("billInfor",bill);
        if(payErr!=null){
            model.addAttribute("payErr","Thanh toán online không khả dụng");
        }else {
            model.addAttribute("payErr",false);
        }
        return "payment";
    }

    @PostMapping("/payment")
    public String updateToBilByPayment(String payment, HttpServletRequest request,Model model){
        if(payment.equals("onl")){
            return payment(model,true);
        }
        billService.updateToBillForPayment(payment,request);
        return "noti/order";
    }

    @PostMapping("/delivery")
    public String updateToBill(@Valid @ModelAttribute("user")UserRequest dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/delivery";
        }
        billService.updateToBillDelivery(dto);
        return "redirect:/payment";
    }
}
