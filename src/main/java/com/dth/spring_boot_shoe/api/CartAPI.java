package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.request.CartRequest;
import com.dth.spring_boot_shoe.response.CheckQuantity;
import com.dth.spring_boot_shoe.response.SizeQuantity;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.ProductService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartAPI {

    private final BillService billService;
    private final UserService userService;

    //detail.js
    @PostMapping("/cart")
    public void productDetail(@RequestBody CartRequest request){
        UserEntity user=userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        billService.addCart(user,request);
    }

    //cart.js
    @PostMapping("/cart/load")
    public Map<String,Object> loadCart(@RequestBody CartRequest[] requests){
        List<BillReceiptDTO> details=billService.loadCart(requests);
        List<SizeQuantity> quantities=billService.findQuantityProductDetail(details);
        HashMap<String,Object> map=new HashMap<>();
        map.put("details",details);
        map.put("quantities",quantities);
        return map;
    }
    @GetMapping("/cart/load")
    public Map<String,Object> loadCart(){
        HashMap<String,Object> map=new HashMap<>();
        List<BillReceiptDTO> details=billService.loadCart();
        if(details==null){
            map.put("details",null);
            return map;
        }
        List<SizeQuantity> quantities=billService.findQuantityProductDetail(details);
        map.put("details",details);
        map.put("quantities",quantities);
        return map;
    }

    @PutMapping("/cart/update")
    public void update(@RequestBody CartRequest request){
        UserEntity user=userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        billService.update(user,request);
    }

    @DeleteMapping("/cart/delete")
    public void delete(@RequestBody CartRequest request){
        UserEntity user=userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        billService.delete(user,request);
    }

    @PostMapping("/cart/check")
    public Map<String,Object> check(@RequestBody CartRequest[] requests){
        List<CheckQuantity> checkQuantities=billService.checkQuantityProductToOrder(requests);
        HashMap<String,Object> map=new HashMap<>();
        map.put("check",checkQuantities);
        return map;
    }
}
