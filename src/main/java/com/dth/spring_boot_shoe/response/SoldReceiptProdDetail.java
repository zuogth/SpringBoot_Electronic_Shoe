package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.SizeEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SoldReceiptProdDetail {
    private Long productDetailId;
    private SizeEntity size;
    private Integer sold;
    private Integer receipt;
    private Integer status;

    public static SoldReceiptProdDetail converter(Long id,Integer status,SizeEntity entity, Integer sold, Integer receipt){
        return SoldReceiptProdDetail.builder().productDetailId(id).size(entity)
                .sold(sold).receipt(receipt).status(status).build();
    }
}
