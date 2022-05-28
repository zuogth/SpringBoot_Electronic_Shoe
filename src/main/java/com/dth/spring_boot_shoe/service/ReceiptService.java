package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.request.ReceiptRequest;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.response.ReceiptDetailResponse;
import com.dth.spring_boot_shoe.response.ReceiptResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ReceiptService {

    Map<String,Object> findAll(int page);

    Map<String,Object> findReceiptDetailById(Long id,int page);

    List<ProductDetailResponse> findProductDetails(Long productId,Long colorId, Long sizeId);

    void insert(ReceiptRequest receiptRequest);

    Map<String,Object> findDetailsById(Long id);

    void update(ReceiptRequest receiptRequest);

    String ExportReceipt(Long id, String type);
}
