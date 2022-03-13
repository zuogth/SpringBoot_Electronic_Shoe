package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.entity.ColorEntity;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.repository.ColorRepository;
import com.dth.spring_boot_shoe.repository.SizeRepository;
import com.dth.spring_boot_shoe.response.Product;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class ProductAPI {

    private final ProductService productService;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> index(@RequestParam(value = "brand_id",required = false) Long brand_id,
                                               @RequestParam("page") int page){
        Map<String,Object> result=productService.findAllByBrand(brand_id,page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getDetailById(@PathVariable("id") Long id,
                                                            @RequestParam("page") int page){
        Map<String,Object> map=new HashMap<>();
        List<ColorEntity> colors=colorRepository.findColorByNotInProductDetail(id);
        List<SizeEntity> sizes=sizeRepository.findAll();
        Map<String,Object> result=productService.findAllDetailByProduct(id,page);
        map.put("colors",colors);
        map.put("product",result.get("product"));
        map.put("sizes",sizes);
        map.put("totalItems",result.get("totalItems"));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(productService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiException> update(@Valid @RequestBody Product product){
        productService.update(product);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiException> insert(@Valid @RequestBody Product product){
        productService.insert(product);
        return new ResponseEntity<>(MessageAdmin.CREATED_SUCCESS,HttpStatus.CREATED);
    }

    @PutMapping("/lock/{id}")
    public ResponseEntity<ApiException> lock(@PathVariable("id") Long id,@RequestParam("status") Integer status){
        productService.lockProd(id,status);
        return new ResponseEntity<>(status==0?MessageAdmin.LOCKED:MessageAdmin.UNLOCKED,HttpStatus.OK);
    }


}
