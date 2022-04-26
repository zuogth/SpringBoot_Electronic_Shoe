package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.service.UserService;
import com.dth.spring_boot_shoe.utils.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final SendMailService sendMailService;


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user")UserDTO user, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "/register";
        }
        //userService.save(user);
        sendMailService.sendHtml(user.getEmail());
        return "redirect:/login";
    }

}
