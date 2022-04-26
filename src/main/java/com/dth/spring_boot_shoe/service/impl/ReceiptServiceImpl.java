package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.constant.MessageErr;
import com.dth.spring_boot_shoe.entity.*;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ReceiptRequest;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.response.ReceiptDetailResponse;
import com.dth.spring_boot_shoe.response.ReceiptResponse;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final EntityManager entityManager;
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final ImageService imageService;

    @Override
    public Map<String, Object> findAll(int page) {
        Map<String,Object> map=new HashMap<>();
        Pageable pageable= PageRequest.of(page-1,10);
        Page<ReceiptEntity> list=receiptRepository.findAll(pageable);
        map.put("totalItems",list.getTotalElements());
        List<ReceiptResponse> responses=new ArrayList<>();
        list.forEach(e->responses.add(ReceiptResponse.converter(e,
                productReceiptRepository.findQuantityTotal(e.getId()))));
        map.put("receipts",responses);
        return map;
    }

    @Override
    public Map<String,Object> findReceiptDetailById(Long id, int page) {
        Map<String,Object> map=new HashMap<>();
        Pageable pageable=PageRequest.of(page-1,10);
        Page<ProductReceiptEntity> entities=productReceiptRepository.findByReceiptIdAndGroupByProductAndColor(id,pageable);
        map.put("totalItemChs",entities.getTotalElements());
        List<ReceiptDetailResponse> responses=new ArrayList<>();
        entities.forEach(e->responses.add(ReceiptDetailResponse.converter(e,
                imageService.findByColorIdAndProductIdAndParent(e.getProductDetail().getColor().getId(),e.getProductDetail().getProduct().getId()),
                productReceiptRepository.findSumReceiptByReceiptIdAndGroupByColor(id,
                        e.getProductDetail().getColor().getId(),
                        e.getProductDetail().getProduct().getId()))));
        map.put("receiptDetails",responses);
        return map;
    }

    @Override
    public List<ProductDetailResponse> findProductDetails(Long productId, Long colorId, Long sizeId) {
        List<ProductDetailEntity> detailEntities;
        List<ProductDetailResponse> responses=new ArrayList<>();
        TypedQuery<ProductDetailEntity> typedQuery;
        String query="select pd from ProductDetailEntity pd where pd.status = 1 ";
        if(productId!=null){
            query+="and pd.product.id="+productId;
        }
        if(colorId!=null){
            query+="and pd.color.id="+colorId;
        }
        if(sizeId!=null){
            query+="and pd.size.id="+sizeId;
        }
        typedQuery=entityManager.createQuery(query,ProductDetailEntity.class);
        detailEntities=typedQuery.getResultList();
        detailEntities.forEach(e->responses.add(ProductDetailResponse.converter(e,
                null,null,
                imageService.findByColorIdAndProductIdAndParent(e.getColor().getId(),e.getProduct().getId()),
                null)));
        return responses;
    }

    @Override
    public void insert(ReceiptRequest receiptRequest) {
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1)
                .orElseThrow(()->new ApiRequestException(MessageErr.UN_LOGIN));
        BrandEntity brand=brandRepository.findById(receiptRequest.getBrandId())
                .orElseThrow(()->new ApiRequestException("Nhà cung cấp không tồn tại!"));
        ReceiptEntity receipt=new ReceiptEntity();
        receipt.setBrand(brand);
        receipt.setUser(user);
        receipt.setTotalprice(receiptRequest.getTotalMoney());
        receipt=receiptRepository.save(receipt);

        ReceiptEntity finalReceipt = receipt;
        receiptRequest.getProductDetails().forEach(e->{
            ProductReceiptEntity productReceiptEntity=new ProductReceiptEntity();
            ProductDetailEntity productDetail=productDetailRepository.findByIdAndStatusNot(e.getId(),-1)
                    .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
            productReceiptEntity.setProductDetail(productDetail);
            productReceiptEntity.setReceipt(finalReceipt);
            productReceiptEntity.setQuantity(e.getQuantity());
            productReceiptRepository.save(productReceiptEntity);
        });
    }
}
