package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.request.CartRequest;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartAPI {

    private final BillService billService;
    private final UserService userService;


    @PostMapping("/cart")
    public void productDetail(@RequestBody CartRequest request){
        UserEntity user=userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        billService.addCart(user,request);
    }

    @PostMapping("/cart/load")
    public ResponseEntity<Object> loadCart(@RequestBody CartRequest[] requests){
        List<BillReceiptDTO> details=billService.loadCart(requests);
        return new ResponseEntity<>(details,HttpStatus.OK);
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
}
