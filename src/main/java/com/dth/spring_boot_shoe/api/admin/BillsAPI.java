package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.repository.ProductBillRepository;
import com.dth.spring_boot_shoe.response.BillDetailResponse;
import com.dth.spring_boot_shoe.response.BillResponse;
import com.dth.spring_boot_shoe.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/bills")
public class BillsAPI {

    private final BillService billService;
    private final ProductBillRepository productBillRepository;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAll(@RequestParam("page") int page,@RequestParam(value = "now",required = false) boolean now){
        return new ResponseEntity<>(billService.getAll(page,now),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BillDetailResponse>> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(billService.findByBillId(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiException> update(@PathVariable("id") Long id,
                                               @RequestParam("status") String status){
        billService.updateBill(id,status);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

}
