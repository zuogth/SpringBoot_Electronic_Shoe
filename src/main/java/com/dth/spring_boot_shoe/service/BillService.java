package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.BillDTO;
import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.request.CartRequest;
import com.dth.spring_boot_shoe.request.UserRequest;
import com.dth.spring_boot_shoe.response.CheckQuantity;
import com.dth.spring_boot_shoe.response.SizeQuantity;

import java.util.List;

public interface BillService {
    BillEntity findByUserId(Long userId);
    void addCart(UserEntity user, CartRequest request);

    List<BillReceiptDTO> loadCart(CartRequest[] request);
    List<BillReceiptDTO> loadCart();

    BillEntity updateCartFromLocal(UserEntity user,CartRequest[] request);

    void update(UserEntity user, CartRequest request);
    void delete(UserEntity user, CartRequest request);

    List<SizeQuantity> findQuantityProductDetail(List<BillReceiptDTO> dtos);

    List<CheckQuantity> checkQuantityProductToOrder(CartRequest[] request);

    List<BillReceiptDTO> updateToBill();
    void updateToBill(UserRequest dto);

    List<BillDTO> findAllBillByUser(Long userId);

    List<BillDTO> findAllHisOrderByUser(Long userId);

    void changePaying();

}
