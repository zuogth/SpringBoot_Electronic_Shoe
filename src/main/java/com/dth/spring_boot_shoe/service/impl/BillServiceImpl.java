package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.ProductBillEntity;
import com.dth.spring_boot_shoe.constant.StatusBill;
import com.dth.spring_boot_shoe.dto.BillDTO;
import com.dth.spring_boot_shoe.dto.BillReceiptDTO;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.BillEntity;
import com.dth.spring_boot_shoe.entity.CommentEntity;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.exception.RequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.CartRequest;
import com.dth.spring_boot_shoe.request.UserRequest;
import com.dth.spring_boot_shoe.response.CheckQuantity;
import com.dth.spring_boot_shoe.response.SizeQuantity;
import com.dth.spring_boot_shoe.service.BillService;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ProductBillRepository productBillRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductReceiptRepository productReceiptRepository;
    private final CommentRepository commentRepository;

    @Override
    public BillEntity findByUserId(Long userId) {
        return billRepository.findByUserIdAndBillType(userId,0);
    }

    @Override
    public void addCart(UserEntity user, CartRequest request) {
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
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
            entities.forEach(entity->
                detailEntities.add(new BillReceiptDTO(entity.getQuantity(),ProductDetailDTO.converter(modelMapper,entity.getProductDetail(),
                        imageService.findByColorIdAndProductIdAndParent(entity.getProductDetail().getColor().getId(),entity.getProductDetail().getProduct().getId())),
                        entity.getPrice()))
            );
            bill.setPaying(0);
            billRepository.save(bill);
            return detailEntities;
        }
        Arrays.stream(request).forEach(cart->{
            ProductDetailEntity entity=productDetailRepository.findById(cart.getProductDetailId()).get();
            detailEntities.add(new BillReceiptDTO(cart.getQuantity(),ProductDetailDTO.converter(modelMapper,entity,
                    imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId())),
                    cart.getPrice()));
        });
        return detailEntities;
    }

    @Override
    public List<BillReceiptDTO> loadCart() {
        List<BillReceiptDTO> detailEntities=new ArrayList<>();
        Optional<UserEntity> user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1);
        if(user.isPresent()){
            BillEntity bill=billRepository.findByUserIdAndBillType(user.get().getId(),0);
            if(bill==null){return null;}
            List<ProductBillEntity> entities=productBillRepository.findByBillId(bill.getId());
            entities.forEach(entity->
                    detailEntities.add(new BillReceiptDTO(entity.getQuantity(),ProductDetailDTO.converter(modelMapper,entity.getProductDetail(),
                            imageService.findByColorIdAndProductIdAndParent(entity.getProductDetail().getColor().getId(),entity.getProductDetail().getProduct().getId())),
                            entity.getPrice()))
            );
            return detailEntities;
        }
        return null;
    }

    @Override
    public BillEntity updateCartFromLocal(UserEntity user,CartRequest[] request) {
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
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
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
        ProductBillEntity productBill=productBillRepository.findByBillIdAndProductDetailId(bill.getId(),request.getProductDetailId());
        productBill.setQuantity(request.getQuantity());
        productBillRepository.save(productBill);
    }

    @Override
    public void delete(UserEntity user, CartRequest request) {
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
        ProductBillEntity productBill=productBillRepository.findByBillIdAndProductDetailId(bill.getId(),request.getProductDetailId());
        productBillRepository.delete(productBill);
    }

    @Override
    public List<SizeQuantity> findQuantityProductDetail(List<BillReceiptDTO> dtos) {
        List<SizeQuantity> quantities=new ArrayList<>();
        dtos.forEach(dto->{
            List<Object[]> objects=productReceiptRepository.findByProductIdGroupBySizeId(dto.getDetail().getId());
            quantities.add(SizeQuantity.converter(objects.get(0)));
        });
        return quantities;
    }

    @Override
    public List<CheckQuantity> checkQuantityProductToOrder(CartRequest[] request) {
        List<CheckQuantity> quantities=new ArrayList<>();
        Arrays.stream(request).forEach(dto->{
            List<Object[]> objects=productReceiptRepository.findByProductIdGroupBySizeId(dto.getProductDetailId());
            SizeQuantity sizeQuantity=SizeQuantity.converter(objects.get(0));

            quantities.add(new CheckQuantity(sizeQuantity, sizeQuantity.getQuantity() < dto.getQuantity()));
        });
        return quantities;
    }

    //get cart to delivery, change paying to 1
    @Override
    public List<BillReceiptDTO> updateToBill() {
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập","login"));
        List<BillReceiptDTO> detailEntities=new ArrayList<>();
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
        if(bill==null){throw new RequestException("Giỏ hàng hiện tại của bạn trống","cart");}
        List<ProductBillEntity> entities=productBillRepository.findByBillId(bill.getId());
        entities.forEach(entity->
                detailEntities.add(new BillReceiptDTO(entity.getQuantity(),ProductDetailDTO.converter(modelMapper,entity.getProductDetail(),
                        imageService.findByColorIdAndProductIdAndParent(entity.getProductDetail().getColor().getId(),entity.getProductDetail().getProduct().getId())),
                        entity.getPrice()))
        );
        bill.setPaying(1);
        billRepository.save(bill);
        return detailEntities;
    }

    //đặt hàng
    @Override
    public void updateToBill(UserRequest dto) {
        UserEntity user=userRepository.findByEmailAndStatus(dto.getEmail(),1)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập","login"));
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
        List<ProductBillEntity> entities=productBillRepository.findByBillId(bill.getId());
        double total=entities.stream().mapToDouble(entity-> entity.getPrice().doubleValue()* (double)entity.getQuantity()).sum();
        if (total<1000000){
            total+=50000;
        }
        BigDecimal tt1=new BigDecimal("0.00");
        BigDecimal tt2=new BigDecimal(total);
        BigDecimal r=tt1.add(tt2);
        bill.setTotalprice(r);
        bill.setBillType(1);
        bill.setPaying(0);
        bill.setStatus(StatusBill.waiting.toString());
        bill.setCreatedAt(LocalDate.now());
        bill.setAddress(dto.getProvince()+"-"+dto.getDistrict()+"-"+dto.getWard()+"-"+dto.getVillage());
        //userService.update(dto,user);
        billRepository.save(bill);
    }

    @Override
    public List<BillDTO> findAllBillByUser(Long userId) {
        List<BillDTO> dtos=new ArrayList<>();
        List<BillEntity> entities=billRepository.findByUserIdAndBillTypeAndStatusNot(userId,1,StatusBill.finished.toString());
        entities.forEach(entity-> {
            List<BillReceiptDTO> billReceiptDTOS=new ArrayList<>();
            List<ProductBillEntity> prodBills = productBillRepository.findByBillId(entity.getId());
            prodBills.forEach(prodBill->
                billReceiptDTOS.add(new BillReceiptDTO(prodBill.getQuantity(),
                        ProductDetailDTO.converter(modelMapper,prodBill.getProductDetail(),
                                imageService.findByColorIdAndProductIdAndParent(prodBill.getProductDetail().getColor().getId(),prodBill.getProductDetail().getProduct().getId())),
                                prodBill.getPrice())));
            dtos.add(new BillDTO(billReceiptDTOS,entity.getTotalprice(),entity.getStatus(),entity.getCreatedAt(),entity.getModifiedAt()));
        });
        return dtos;
    }

    @Override
    public List<BillDTO> findAllHisOrderByUser(Long userId) {
        List<BillDTO> dtos=new ArrayList<>();
        List<BillEntity> entities=billRepository.findByUserIdAndBillTypeAndStatus(userId,1,StatusBill.finished.toString());
        entities.forEach(entity-> {
            List<BillReceiptDTO> billReceiptDTOS=new ArrayList<>();
            List<ProductBillEntity> prodBills = productBillRepository.findByBillId(entity.getId());
            prodBills.forEach(prodBill->{
                    ProductDetailDTO dto=ProductDetailDTO.converter(modelMapper,prodBill.getProductDetail(),
                            imageService.findByColorIdAndProductIdAndParent(prodBill.getProductDetail().getColor().getId(),prodBill.getProductDetail().getProduct().getId()));
                Optional<CommentEntity> comment=commentRepository.findByUserIdAndAndProductId(userId,dto.getProduct().getId());
                if(comment.isPresent()){
                    dto.setRate(true);
                }
                    billReceiptDTOS.add(new BillReceiptDTO(prodBill.getQuantity(),dto,prodBill.getPrice()));
            });
                dtos.add(new BillDTO(billReceiptDTOS,entity.getTotalprice(),entity.getStatus(),entity.getCreatedAt(),entity.getModifiedAt()));
        });
        return dtos;
    }

    @Override
    public void changePaying() {
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập","login"));
        BillEntity bill=billRepository.findByUserIdAndBillType(user.getId(),0);
        bill.setPaying(0);
        billRepository.save(bill);
    }
}
