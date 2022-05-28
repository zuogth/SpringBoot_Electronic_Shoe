package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.*;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.request.CartRequest;
import com.dth.spring_boot_shoe.request.UserRequest;
import com.dth.spring_boot_shoe.response.BillDetailResponse;
import com.dth.spring_boot_shoe.response.BillResponse;
import com.dth.spring_boot_shoe.response.CheckQuantity;
import com.dth.spring_boot_shoe.response.SizeQuantity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BillService {
    BillEntity findByUserId();
    ProductDetailDTO addCart(UserEntity user, CartRequest request);

    List<BillReceiptDTO> loadCart(CartRequest[] request);
    List<BillReceiptDTO> loadCartFixed(CartRequest[] request);
    List<BillReceiptDTO> loadCart();

    BillEntity updateCartFromLocal(UserEntity user,CartRequest[] request);

    void update(UserEntity user, CartRequest request);
    void delete(UserEntity user, CartRequest request);

    List<SizeQuantity> findQuantityProductDetail(List<BillReceiptDTO> dtos);

    List<CheckQuantity> checkQuantityProductToOrder(CartRequest[] request);

    List<BillReceiptDTO> updateToBill();
    void updateToBillDelivery(UserRequest dto);
    void updateToBillForPayment(String payment,HttpServletRequest request);

    List<BillDTO> findAllBillByUser(Long userId);

    List<BillDTO> findAllHisOrderByUser(Long userId);

    void changePaying();

    //admin

    Map<String,Object> getAll(int page,boolean now);

    List<BillDetailResponse> findByBillId(Long billId);

    void updateBill(Long id,String status);

}
