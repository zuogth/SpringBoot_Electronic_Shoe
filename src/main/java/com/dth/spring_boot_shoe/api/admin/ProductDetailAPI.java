package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.repository.ProductDetailRepository;
import com.dth.spring_boot_shoe.repository.SizeRepository;
import com.dth.spring_boot_shoe.request.ProductDetail;
import com.dth.spring_boot_shoe.response.SoldReceiptProdDetail;
import com.dth.spring_boot_shoe.service.FirebaseService;
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
@RequestMapping("/api/admin/productdetails")
@RequiredArgsConstructor
public class ProductDetailAPI {

    private final ProductDetailService productDetailService;
    private final SizeRepository sizeRepository;
    private final SizeService sizeService;

    @PostMapping("")
    public ResponseEntity<ApiException> insert(@Valid ProductDetail productDetail) throws IOException {
        productDetailService.insert(productDetail);
        return new ResponseEntity<>(MessageAdmin.CREATED_SUCCESS, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> getById(@RequestParam("productId") Long productId,
                                                      @RequestParam("colorId") Long colorId){
        return new ResponseEntity<>(productDetailService.findById(productId,colorId),HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ApiException> update(ProductDetail productDetail) throws IOException {
        productDetailService.update(productDetail);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @PutMapping("/sizes")
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

    @PostMapping("/sizes")
    public ResponseEntity<List<SoldReceiptProdDetail>> insertSize(@RequestBody ProductDetail productDetail,
                                                   @RequestParam(name = "sizeId",required = false) Long sizeId){
        productDetailService.insertSize(productDetail,sizeId);
        return new ResponseEntity<>(sizeService.findSizeNotInProductDetail(productDetail.getProductId(),productDetail.getColorId()),HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiException> lock(@PathVariable("id") Long id,
                                             @RequestParam("status") Integer status){
        productDetailService.lock(id,status);
        return new ResponseEntity<>(status==0?MessageAdmin.LOCKED:MessageAdmin.UNLOCKED,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiException> delete(@PathVariable("id") Long id,
                                               @RequestParam("flag") int flag){
        if(flag==1){
            return  new ResponseEntity<>(productDetailService.deleteSingle(id),HttpStatus.OK);
        }else {
            productDetailService.delete(id);
        }
        return new ResponseEntity<>(MessageAdmin.DELETED_SUCCESS,HttpStatus.OK);
    }
}
