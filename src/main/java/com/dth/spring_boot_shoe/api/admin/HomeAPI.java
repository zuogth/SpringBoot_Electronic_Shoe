package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.response.ChartResponse;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class HomeAPI {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> index(){
        Map<String,Object> map = new HashMap<>();
        map.put("initRes",productService.getAll());
        map.put("getChartRes",productService.getChart(LocalDate.now().getYear()));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/chart")
    public ResponseEntity<Map<String,Object>> getChart(@RequestParam("year") int year){
        return new ResponseEntity<>(productService.getChart(year),HttpStatus.OK);
    }


}
