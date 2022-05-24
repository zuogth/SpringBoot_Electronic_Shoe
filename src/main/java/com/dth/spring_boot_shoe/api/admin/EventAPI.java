package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.entity.BrandEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.repository.BrandRepository;
import com.dth.spring_boot_shoe.repository.DiscountRepository;
import com.dth.spring_boot_shoe.repository.ProductRepository;
import com.dth.spring_boot_shoe.request.DiscountRequest;
import com.dth.spring_boot_shoe.request.EventRequest;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
public class EventAPI {

    private final EventService eventService;
    private final DiscountRepository discountRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam("page") int page){
        return new ResponseEntity<>(eventService.getAll(page),HttpStatus.OK);
    }

    @GetMapping("/datas")
    public ResponseEntity<?> getBrands(){
        Map<String,Object> map=new HashMap<>();
        List<BrandEntity> brands=brandRepository.findAll();
        List<ProductEntity> products = productRepository.findByStatus(1);
        map.put("brands",brands);
        map.put("products",products);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/productDetails")
    public ResponseEntity<?> findProductDetail(@RequestParam("h") Long h,
                                               @RequestParam(name = "brandId",required = false) Long brandId,
                                               @RequestParam(name="productId",required = false) Long productId){
        Map<String,Object> map=new HashMap<>();
        List<ProductDetailResponse> responses = eventService.findProductDetails(brandId,productId);
        if(productId==null){
            List<ProductEntity> products = productRepository.findByBrandIdAndStatus(brandId,1);
            map.put("products",products);
        }
        map.put("productDetails",responses);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(eventService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getProducts(@PathVariable("type") String type,
                                         @RequestParam("id") Long id){
        return new ResponseEntity<>(eventService.getProducts(type,id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid EventRequest discount){
        Map<String,Object> map = new HashMap<>();
        map.put("event",eventService.insert(discount));
        map.put("message","Thêm sự kiện thành công, hãy chọn các sản phẩm áp dụng khuyến mãi");
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    @GetMapping("/discounts/{eventId}")
    public ResponseEntity<?> getDis(@PathVariable("eventId") Long eventId){

        return new ResponseEntity<>(discountRepository.findByEventId(eventId),HttpStatus.OK);
    }

    @PostMapping("/discounts")
    public ResponseEntity<?> insertDis(@RequestBody  DiscountRequest[] discounts){
        eventService.insertDis(discounts);
        return new ResponseEntity<>(MessageAdmin.SAVE_DIS,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        eventService.delete(id);
        return new ResponseEntity<>(MessageAdmin.DELETED_SUCCESS,HttpStatus.OK);
    }

    @PutMapping("/show/{id}")
    public ResponseEntity<?> updateShow(@PathVariable("id") Long id,
                                        @RequestParam("show") Integer showWeb){
        eventService.updateShow(id,showWeb);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }
}
