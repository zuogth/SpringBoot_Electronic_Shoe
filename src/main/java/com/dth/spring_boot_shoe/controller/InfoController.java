package com.dth.spring_boot_shoe.controller;

import com.dth.spring_boot_shoe.dto.BillDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    private final UserService userService;
    private final BillService billService;

    @GetMapping("/{name}")
    public String profile(@PathVariable("name") String name, Model model){
        if(!name.equals("profile") && !name.equals("order")){
            return "redirect:/";
        }
        UserEntity user=userService.profile();
        List<BillDTO> dtos=billService.findAllBillByUser(user.getId());
        List<BillDTO> hisOrder=billService.findAllHisOrderByUser(user.getId());
        List<BillDTO> cancelOrder=billService.findAllCancelBillByUser(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("bills",dtos);
        model.addAttribute("orders",hisOrder);
        model.addAttribute("cancelOrders",cancelOrder);
        return "info";
    }

}
