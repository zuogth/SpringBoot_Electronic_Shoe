package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.repository.ColorRepository;
import com.dth.spring_boot_shoe.repository.ProductRepository;
import com.dth.spring_boot_shoe.repository.SizeRepository;
import com.dth.spring_boot_shoe.request.ReceiptRequest;
import com.dth.spring_boot_shoe.response.Product;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/receipts")
public class ReceiptAPI {

    private final ReceiptService receiptService;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> index(@RequestParam("page") int page){
        return new ResponseEntity<>(receiptService.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable("id") Long id,
                                                                @RequestParam("page") int page){
        return new ResponseEntity<>(receiptService.findReceiptDetailById(id,page),HttpStatus.OK);
    }

    @GetMapping("/productDetails")
    public ResponseEntity<List<ProductDetailResponse>> findProductDetail(@RequestParam("h") Long h,
                                                                         @RequestParam(name = "productId",required = false) Long productId,
                                                                         @RequestParam(name="colorId",required = false) Long colorId,
                                                                         @RequestParam(name="sizeId",required = false) Long sizeId){
        return new ResponseEntity<>(receiptService.findProductDetails(productId,colorId,sizeId),HttpStatus.OK);
    }

    @GetMapping("/products/{brandId}")
    public ResponseEntity<Map<String,Object>> getProductByBrand(@PathVariable("brandId") Long brandId){
        Map<String,Object> map=new HashMap<>();
        map.put("colors",colorRepository.findAll());
        map.put("sizes",sizeRepository.findAll());
        map.put("products",productRepository.findByBrandIdAndStatusNot(brandId,-1));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiException> insert(@RequestBody ReceiptRequest receiptRequest){
        //receiptService.insert(receiptRequest);
        return new ResponseEntity<>(MessageAdmin.CREATED_SUCCESS,HttpStatus.CREATED);
    }
}
