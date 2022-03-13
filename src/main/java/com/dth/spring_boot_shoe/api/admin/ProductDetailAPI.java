package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.repository.ProductDetailRepository;
import com.dth.spring_boot_shoe.repository.SizeRepository;
import com.dth.spring_boot_shoe.request.ProductDetail;
import com.dth.spring_boot_shoe.response.SoldReceiptProdDetail;
import com.dth.spring_boot_shoe.service.ProductDetailService;
import com.dth.spring_boot_shoe.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/productdetail")
@RequiredArgsConstructor
public class ProductDetailAPI {

    private final ProductDetailService productDetailService;
    private final SizeRepository sizeRepository;
    private final SizeService sizeService;

    @PostMapping("/insert")
    public ResponseEntity<ApiException> insert(@Valid ProductDetail productDetail){
        productDetailService.insert(productDetail);
        return new ResponseEntity<>(MessageAdmin.CREATED_SUCCESS, HttpStatus.CREATED);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(productDetailService.findById(id),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiException> update(ProductDetail productDetail) throws IOException {
        productDetailService.update(productDetail);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @PutMapping("/update/size")
    public ResponseEntity<List<SoldReceiptProdDetail>> updateSize(@RequestBody ProductDetail productDetail,
                                                                  @RequestParam(name = "sizeId",required = false) Long sizeId){
        productDetailService.updateSizeForProductDetail(productDetail.getId(), sizeId);
        return new ResponseEntity<>(sizeService.findSizeNotInProductDetail(productDetail.getProductId(),productDetail.getColorId()),HttpStatus.OK);
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<SizeEntity>> getSize(@RequestParam("productId") Long productId,
                                                    @RequestParam("colorId") Long colorId){
        return new ResponseEntity<>(sizeRepository.findSizeNotInProductDetail(productId,colorId),HttpStatus.OK);
    }

    @PostMapping("/insert/size")
    public ResponseEntity<List<SoldReceiptProdDetail>> insertSize(@RequestBody ProductDetail productDetail,
                                                   @RequestParam(name = "sizeId",required = false) Long sizeId){
        productDetailService.insertSize(productDetail,sizeId);
        return new ResponseEntity<>(sizeService.findSizeNotInProductDetail(productDetail.getProductId(),productDetail.getColorId()),HttpStatus.OK);
    }

    @PutMapping("/lock/{id}")
    public ResponseEntity<ApiException> lock(@PathVariable("id") Long id,
                                             @RequestParam("status") Integer status){
        productDetailService.lock(id,status);
        return new ResponseEntity<>(status==0?MessageAdmin.LOCKED:MessageAdmin.UNLOCKED,HttpStatus.OK);
    }
}
