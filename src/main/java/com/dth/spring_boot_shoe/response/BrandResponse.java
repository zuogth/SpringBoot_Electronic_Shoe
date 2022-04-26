package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.BrandEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandResponse {

    private BrandEntity info;
    private Integer sold;
    private Integer receipt;

    public static BrandResponse converter(BrandEntity entity,Integer sold,Integer receipt){
        return BrandResponse.builder().info(entity).sold(sold).receipt(receipt).build();
    }
}
