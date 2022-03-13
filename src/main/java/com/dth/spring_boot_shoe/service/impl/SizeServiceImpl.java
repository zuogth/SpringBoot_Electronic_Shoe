package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.repository.ProductBillRepository;
import com.dth.spring_boot_shoe.repository.ProductDetailRepository;
import com.dth.spring_boot_shoe.repository.ProductReceiptRepository;
import com.dth.spring_boot_shoe.response.SoldReceiptProdDetail;
import com.dth.spring_boot_shoe.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final ProductBillRepository productBillRepository;

    @Override
    public List<SoldReceiptProdDetail> findSizeNotInProductDetail(Long productId, Long colorId) {
        List<ProductDetailEntity> list=productDetailRepository.findByProductIdAndColorIdAndStatusNot(productId,colorId,-1);
        List<SoldReceiptProdDetail> soldReceiptProdDetails=new ArrayList<>();
        list.forEach(e->soldReceiptProdDetails.add(SoldReceiptProdDetail.converter(e.getId(),e.getStatus(),e.getSize(),
                Integer.valueOf(productBillRepository.findSumSoldByEachProductDetail(e.getId()).get(0)[0].toString()),
                Integer.valueOf(productReceiptRepository.findSumReceiptByEachProductDetail(e.getId()).get(0)[0].toString()))));

        return soldReceiptProdDetails;
    }
}
