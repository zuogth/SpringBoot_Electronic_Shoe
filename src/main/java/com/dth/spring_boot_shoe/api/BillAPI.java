package com.dth.spring_boot_shoe.api;

import com.dth.spring_boot_shoe.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BillAPI {

    private final BillService billService;

    @PutMapping("/bill/update")
    public void updatePaying(){
        billService.changePaying();
    }
}
