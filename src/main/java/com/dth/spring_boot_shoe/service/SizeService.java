package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.response.SoldReceiptProdDetail;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeService {

    List<SoldReceiptProdDetail> findSizeNotInProductDetail(Long productId,Long colorId);
}
