package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.constant.MessageErr;
import com.dth.spring_boot_shoe.entity.BrandEntity;
import com.dth.spring_boot_shoe.entity.ColorEntity;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ColorRequest;
import com.dth.spring_boot_shoe.response.BrandResponse;
import com.dth.spring_boot_shoe.service.FirebaseService;
import com.dth.spring_boot_shoe.service.PropertiesService;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PropertiesServiceImpl implements PropertiesService {

    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final BrandRepository brandRepository;
    private final ProductBillRepository productBillRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final FirebaseService firebaseService;

    @Override
    public Map<String, Object> getAll() {
        Map<String,Object> map=new HashMap<>();
        Pageable pageable= PageRequest.of(0,10);
        Page<ColorEntity> colors=colorRepository.findAll(pageable);

        Page<SizeEntity> sizes=sizeRepository.findAll(pageable);

        map.put("colors",colors.getContent());
        map.put("sizes",sizes.getContent());
        map.put("totalColors",colors.getTotalElements());
        map.put("totalSizes",sizes.getTotalElements());
        map.put("brands",getBrands());

        return map;
    }

    @Override
    public Map<String, Object> getPage(int page, String flag) {
        Map<String,Object> map=new HashMap<>();
        Pageable pageable= PageRequest.of(page-1,10);
        if(flag.equals("color")){
            Page<ColorEntity> colorEntities=colorRepository.findAll(pageable);
            map.put("totalColors",colorEntities.getTotalElements());
            map.put("colors",colorEntities.getContent());
        }else {
            Page<SizeEntity> sizeEntities=sizeRepository.findAll(pageable);
            map.put("totalSizes",sizeEntities.getTotalElements());
            map.put("sizes",sizeEntities.getContent());
        }
        return map;
    }

    @Override
    public void colorUpdate(ColorRequest colorRequest) {
        ColorEntity color=colorRepository.findById(colorRequest.getId())
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_COLOR));
        Optional<ColorEntity> entity=colorRepository.findBySlug(StringUtils.removeAccent(colorRequest.getName()));
        if(entity.isPresent()){
            if(!Objects.equals(entity.get().getId(), colorRequest.getId())){
                throw new ApiRequestException(MessageErr.COLOR_EXISTS);
            }
        }
        color.setName(colorRequest.getName());
        color.setSlug(StringUtils.removeAccent(colorRequest.getName()));
        color.setCode(colorRequest.getCode());
        colorRepository.save(color);
    }

    @Override
    public void sizeUpdate(Long id, Integer size) {
        SizeEntity entity=sizeRepository.findById(id)
                .orElseThrow(()-> new ApiRequestException(MessageErr.NOT_FOUND_SIZE));
        Optional<SizeEntity> sizeEntity=sizeRepository.findByName(size);
        if(sizeEntity.isPresent()){
            if(!Objects.equals(id,sizeEntity.get().getId())){
                throw new ApiRequestException("Cỡ này đã tồn tại!");
            }
        }
        entity.setName(size);
        sizeRepository.save(entity);
    }

    @Override
    public void brandUpdate(Long id, MultipartFile file) {
        BrandEntity brand=brandRepository.findById(id).orElseThrow(()->new ApiRequestException("Brand không tồn tại!"));
        try {
            firebaseService.deleteImage(brand.getBanner(),"banner");
            String banner=firebaseService.upLoadFile(file,"banner");
            String url=firebaseService.getUrl(banner,"banner");
            brand.setBanner(banner);
            brand.setUrl(url);
            brandRepository.save(brand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BrandResponse> getBrands() {
        List<BrandResponse> brandResponses=new ArrayList<>();
        List<BrandEntity> brands=brandRepository.findAll();
        brands.forEach(b->brandResponses.add(BrandResponse.converter(b,
                productBillRepository.findSumProductByBrand(b.getSlug()),
                productReceiptRepository.findSumProductByBrand(b.getSlug()))));
        return brandResponses;
    }

    @Override
    public void colorInsert(ColorRequest colorRequest) {
        Optional<ColorEntity> entity=colorRepository.findBySlug(StringUtils.removeAccent(colorRequest.getName()));
        if(entity.isPresent()){
            throw new ApiRequestException(MessageErr.COLOR_EXISTS);
        }
        ColorEntity color=new ColorEntity();
        color.setName(colorRequest.getName());
        color.setSlug(StringUtils.removeAccent(colorRequest.getName()));
        color.setCode(colorRequest.getCode());
        colorRepository.save(color);
    }

    @Override
    public void sizeInsert(Integer size) {
        Optional<SizeEntity> sizeEntity=sizeRepository.findByName(size);
        if(sizeEntity.isPresent()){
            throw new ApiRequestException("Cỡ này đã tồn tại!");
        }
        SizeEntity entity=new SizeEntity();
        entity.setName(size);
        sizeRepository.save(entity);
    }
}
