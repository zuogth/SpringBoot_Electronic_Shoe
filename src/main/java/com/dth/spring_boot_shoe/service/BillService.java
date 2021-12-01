package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.request.CartRequest;

import java.util.List;

public interface BillService {
    BillEntity findByUserId(Long userId);
    void addCart(UserEntity user, CartRequest request);

    List<BillReceiptDTO> loadCart(CartRequest[] request);

    BillEntity updateCartFromLocal(UserEntity user,CartRequest[] request);

    void update(UserEntity user, CartRequest request);
    void delete(UserEntity user, CartRequest request);
}
