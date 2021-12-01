package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.ProductBillEntity;
import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.BillRepository;
import com.dth.spring_boot_shoe.repository.ProductBillRepository;
import com.dth.spring_boot_shoe.repository.ProductDetailRepository;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.request.CartRequest;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ProductBillRepository productBillRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private final UserRepository userRepository;

    @Override
    public BillEntity findByUserId(Long userId) {
        return billRepository.findByUserId(userId);
    }

    @Override
    public void addCart(UserEntity user, CartRequest request) {
        BillEntity bill=billRepository.findByUserId(user.getId());
        ProductDetailEntity productDetail=productDetailRepository.findById(request.getProductDetailId()).get();
        if(bill==null){
            bill=new BillEntity();
            bill.setUser(user);
            bill.setBillType(0);
            bill=billRepository.save(bill);
        }
        ProductBillEntity productBill=productBillRepository.findByBillIdAndProductDetailId(bill.getId(),request.getProductDetailId());
        if(productBill==null){
            productBill=new ProductBillEntity();
            productBill.setProductDetail(productDetail);
            productBill.setBill(bill);
            productBill.setPrice(request.getPrice());
            productBill.setQuantity(1);
        }else {
            productBill.setQuantity(productBill.getQuantity()+1);
        }
        productBillRepository.save(productBill);
    }

    @Override
    public List<BillReceiptDTO> loadCart(CartRequest[] request) {
        List<BillReceiptDTO> detailEntities=new ArrayList<>();
        Optional<UserEntity> user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1);
        if(user.isPresent()){
            BillEntity bill=updateCartFromLocal(user.get(),request);
            List<ProductBillEntity> entities=productBillRepository.findByBillId(bill.getId());
            entities.stream().forEach(entity->
                detailEntities.add(new BillReceiptDTO(entity.getQuantity(),ProductDetailDTO.converter(modelMapper,entity.getProductDetail(),
                        imageService.findByColorIdAndProductIdAndParent(entity.getProductDetail().getColor().getId(),entity.getProductDetail().getProduct().getId()))))
            );
            return detailEntities;
        }
        Arrays.stream(request).forEach(cart->{
            ProductDetailEntity entity=productDetailRepository.findById(cart.getProductDetailId()).get();
            detailEntities.add(new BillReceiptDTO(cart.getQuantity(),ProductDetailDTO.converter(modelMapper,entity,
                    imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        });
        return detailEntities;
    }

    @Override
    public BillEntity updateCartFromLocal(UserEntity user,CartRequest[] request) {
        BillEntity bill=billRepository.findByUserId(user.getId());
        if(bill==null){
            bill=new BillEntity();
            bill.setUser(user);
            bill.setBillType(0);
            bill=billRepository.save(bill);
        }
        BillEntity finalBill = bill;
        Arrays.stream(request).forEach(cart->{
            ProductDetailEntity productDetail=productDetailRepository.findById(cart.getProductDetailId()).get();
            ProductBillEntity productBill=productBillRepository.findByBillIdAndProductDetailId(finalBill.getId(),cart.getProductDetailId());
            if(productBill==null){
                productBill=new ProductBillEntity();
                productBill.setProductDetail(productDetail);
                productBill.setBill(finalBill);
                productBill.setPrice(cart.getPrice());
                productBill.setQuantity(cart.getQuantity());
            }else {
                productBill.setQuantity(cart.getQuantity());
            }
            productBillRepository.save(productBill);
        });
        return finalBill;
    }

    @Override
    public void update(UserEntity user, CartRequest request) {
        BillEntity bill=billRepository.findByUserId(user.getId());
        ProductBillEntity productBill=productBillRepository.findByBillIdAndProductDetailId(bill.getId(),request.getProductDetailId());
        productBill.setQuantity(request.getQuantity());
        productBillRepository.save(productBill);
    }

    @Override
    public void delete(UserEntity user, CartRequest request) {
        BillEntity bill=billRepository.findByUserId(user.getId());
        ProductBillEntity productBill=productBillRepository.findByBillIdAndProductDetailId(bill.getId(),request.getProductDetailId());
        productBillRepository.delete(productBill);
    }
}
