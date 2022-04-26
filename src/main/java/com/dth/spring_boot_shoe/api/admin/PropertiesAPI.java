package com.dth.spring_boot_shoe.api.admin;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.constant.MessageErr;
import com.dth.spring_boot_shoe.entity.BrandEntity;
import com.dth.spring_boot_shoe.entity.ColorEntity;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.ColorRepository;
import com.dth.spring_boot_shoe.repository.ProductBillRepository;
import com.dth.spring_boot_shoe.repository.SizeRepository;
import com.dth.spring_boot_shoe.request.ColorRequest;
import com.dth.spring_boot_shoe.response.BrandResponse;
import com.dth.spring_boot_shoe.service.PropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/properties")
public class PropertiesAPI {

    private final PropertiesService propertiesService;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductBillRepository productBillRepository;


    @GetMapping
    public ResponseEntity<Map<String,Object>> getAll(){
        return new ResponseEntity<>(propertiesService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{page}")
    public ResponseEntity<Map<String,Object>> getPage(@PathVariable("page") int page,
                                                      @RequestParam("flag") String flag){
        return new ResponseEntity<>(propertiesService.getPage(page,flag),HttpStatus.OK);
    }

    @GetMapping("/sizes/{id}")
    public ResponseEntity<SizeEntity> getSizeById(@PathVariable("id") Long id){
        return new ResponseEntity<>(sizeRepository.findById(id)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_SIZE)),HttpStatus.OK);
    }

    @GetMapping("/colors/{id}")
    public ResponseEntity<ColorEntity> getColorById(@PathVariable("id") Long id){
        return new ResponseEntity<>(colorRepository.findById(id)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_COLOR)),HttpStatus.OK);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands(){
        return new ResponseEntity<>(propertiesService.getBrands(),HttpStatus.OK);
    }

    @PutMapping("/colors")
    public ResponseEntity<ApiException> colorUpdate(@Valid @RequestBody ColorRequest color){
        propertiesService.colorUpdate(color);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @PutMapping("/sizes/{id}")
    public ResponseEntity<ApiException> sizeUpdate(@RequestParam("size") Integer size,
                                                   @PathVariable("id") Long id){
        propertiesService.sizeUpdate(id,size);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<ApiException> brandUpdate(@RequestBody MultipartFile url,
                                                    @PathVariable("id") Long id){
        propertiesService.brandUpdate(id,url);
        return new ResponseEntity<>(MessageAdmin.UPDATED_SUCCESS,HttpStatus.OK);
    }

    @PostMapping("/colors")
    public ResponseEntity<ApiException> colorInsert(@Valid @RequestBody ColorRequest color){
        propertiesService.colorInsert(color);
        return new ResponseEntity<>(MessageAdmin.CREATED_SUCCESS,HttpStatus.OK);
    }

    @PostMapping("/sizes")
    public ResponseEntity<ApiException> sizeInsert(@RequestParam("size") Integer size){
        propertiesService.sizeInsert(size);
        return new ResponseEntity<>(MessageAdmin.CREATED_SUCCESS,HttpStatus.OK);
    }
}
