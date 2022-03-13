package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.ImageEntity;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.ImageRepository;
import com.dth.spring_boot_shoe.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    @Override
    public List<String> findByColorIdAndProductId(Long color_id, Long product_id) {
        List<ImageEntity> imageEntities=imageRepository.findByColorIdAndProductId(color_id,product_id);
        List<String> images=new ArrayList<>();
        imageEntities.forEach(image->images.add(image.getImage()));
        return images;
    }

    @Override
    public String findByColorIdAndProductIdAndParent(Long color_id, Long product_id) {
        Optional<ImageEntity> entity=imageRepository.findByColorIdAndProductIdAndParent(color_id,product_id,1);
        if (!entity.isPresent()){
            return "";
        }
        return entity.get().getImage();
    }

    @Override
    public List<String> findByColorAndProductAndParentNot(Long color_id, Long product_id) {
        List<ImageEntity> imageEntities=imageRepository.findByColorIdAndProductIdAndParentNot(color_id,product_id,1);
        List<String> images=new ArrayList<>();
        imageEntities.forEach(image->images.add(image.getImage()));
        return images;
    }
}
