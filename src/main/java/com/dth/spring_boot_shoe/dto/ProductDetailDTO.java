package com.dth.spring_boot_shoe.dto;

import com.dth.spring_boot_shoe.entity.ColorEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.entity.SizeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
public class ProductDetailDTO extends BaseDTO{

    private ColorEntity color;
    private SizeEntity size;
    private String image;
    private ProductEntity product;


    public static ProductDetailDTO converter(ModelMapper modelMapper,ProductDetailEntity entity,String image){
        ProductDetailDTO dto=modelMapper.map(entity,ProductDetailDTO.class);
        dto.setImage(image);
        return dto;
    }
}
