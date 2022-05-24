package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;


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
    public String register(@Valid @ModelAttribute("user")UserDTO user,
                           BindingResult bindingResult,
                           HttpServletRequest request) throws Exception {
        if(bindingResult.hasErrors()){
            return "/register";
        }
        userService.save(user,request);
        return "redirect:/login?verify";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code){
        if(userService.verify(code)){
            return "noti/verify";
        }else {
            return "noti/verify_err";
        }
    }

}
