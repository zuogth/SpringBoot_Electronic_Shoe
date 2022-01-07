package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.ProductReceiptEntity;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ProductFilter;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.response.SizeQuantity;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceEmpl implements ProductService {

    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductEntity> findByBrandId(Long brand_id) {
        return productRepository.findByBrandId(brand_id);
    }

    @Override
    public List<ProductDetailDTO> findByBrandIdGroupByProductIdAndColorId(Long brand_id) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findByBrandIdGroupByProductIdAndColorId(brand_id);
        List<ProductDetailDTO> sameDTOS=new ArrayList<>();
        detailEntities.forEach(entity ->
                sameDTOS.add(ProductDetailDTO.converter(modelMapper,entity,imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return sameDTOS;
    }

    @Override
    public ProductDetailDTO findById(Long id) {
        Optional<ProductDetailEntity> prod= productDetailRepository.findById(id);
        return ProductDetailDTO.converter(modelMapper,prod.get(),imageService.findByColorIdAndProductIdAndParent(prod.get().getColor().getId(),prod.get().getProduct().getId()));
    }

    @Override
    public List<SizeQuantity> findAllSizeByProductId(Long id, Long color_id) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findByProductIdAndColorId(id,color_id);
        List<SizeQuantity> detailDTOS=new ArrayList<>();
        detailEntities.stream().forEach(entity->{
            List<Object[]> objects=productReceiptRepository.findByProductIdGroupBySizeId(entity.getId());
            detailDTOS.add(SizeQuantity.converter(objects.get(0)));
        });
        return detailDTOS;
    }

    @Override
    public List<ProductDetailDTO> findSameProduct(Long product_id) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findByProductIdGroupByProductIdAndColorId(product_id);
        List<ProductDetailDTO> detailDTOs=new ArrayList<>();
        detailEntities.stream().forEach(entity->detailDTOs.add(ProductDetailDTO.converter(modelMapper,entity,imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return detailDTOs;
    }

    @Override
    public List<ProductDetailDTO> findBySlug(String slug) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findBySlugProduct(slug);
        List<ProductDetailDTO> detailDTOs=new ArrayList<>();
        detailEntities.stream().forEach(entity->detailDTOs.add(ProductDetailDTO.converter(modelMapper,entity,imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return detailDTOs;
    }

    @Override
    public List<ProductEntity> findProductByBrandAndSize(ProductFilter productFilter) {
        List<ProductEntity> entities;
        TypedQuery<ProductEntity> typedQuery;
        String query;
        String querySort="";
        String queryPrice="";
        if(productFilter.getPrice()!=""){
            queryPrice=" and pd.product.price "+productFilter.getPrice();
        }
        if(productFilter.getSort()!=""){
            querySort=" order by pd.product."+productFilter.getSort();
        }
        if(productFilter.getSizeId() == -1 && productFilter.getColorId() == -1){
            query="select pd.product from ProductDetailEntity pd where pd.product.brand.id=:brand_id"+queryPrice+
                    " group by pd.product.id "+querySort;
            typedQuery=entityManager.createQuery(query,ProductEntity.class);
        }else {
            query="select pd.product from ProductDetailEntity pd " +
                    "where pd.product.brand.id=:brand_id and pd.size.id=:size_id" +
                    " group by pd.product.id";
            typedQuery=entityManager.createQuery(query,ProductEntity.class);
            typedQuery.setParameter("size_id",productFilter.getSizeId());
        }
        typedQuery.setParameter("brand_id",productFilter.getBrandId());
        entities=typedQuery.getResultList();
        return entities;
    }

    @Override
    public List<ProductDetailDTO> findByBrandAndOptions(ProductFilter productFilter) {
        List<ProductDetailEntity> detailEntities;
        List<ProductDetailDTO> detailDTOs=new ArrayList<>();
        TypedQuery<ProductDetailEntity> typedQuery;
        String query;
        String querySort="";
        String queryPrice="";
        if(productFilter.getPrice()!=""){
            queryPrice=" and pd.product.price "+productFilter.getPrice();
        }
        if(productFilter.getSort()!=""){
            querySort=" order by pd.product."+productFilter.getSort();
        }
        if(productFilter.getSizeId() == -1 && productFilter.getColorId() == -1) {
            query="select pd from ProductDetailEntity pd where pd.product.brand.id=:brand_id"+queryPrice+
                    " group by pd.color.id,pd.product.id "+querySort;
            typedQuery=entityManager.createQuery(query,ProductDetailEntity.class);
        }else if(productFilter.getColorId()==-1){
            query="select pd from ProductDetailEntity pd " +
                    "where pd.product.brand.id=:brand_id and pd.size.id=:size_id "+queryPrice+querySort;
            typedQuery=entityManager.createQuery(query,ProductDetailEntity.class);
            typedQuery.setParameter("size_id",productFilter.getSizeId());
        }else if(productFilter.getSizeId() == -1){
            query="select pd from ProductDetailEntity pd " +
                    "where pd.product.brand.id=:brand_id and pd.color.id=:color_id " +queryPrice+
                    "group by pd.product.id"+querySort;
            typedQuery=entityManager.createQuery(query,ProductDetailEntity.class);
            typedQuery.setParameter("color_id",productFilter.getColorId());
        }else{
            query="select pd from ProductDetailEntity pd " +
                    "where pd.product.brand.id=:brand_id and pd.color.id=:color_id and pd.size.id=:size_id"+queryPrice+querySort;
            typedQuery=entityManager.createQuery(query,ProductDetailEntity.class);
            typedQuery.setParameter("color_id",productFilter.getColorId());
            typedQuery.setParameter("size_id",productFilter.getSizeId());
        }
        typedQuery.setParameter("brand_id",productFilter.getBrandId());
        detailEntities=typedQuery.getResultList();
        detailEntities.stream()
                .forEach(entity->detailDTOs.add(ProductDetailDTO.converter(modelMapper,entity,
                        imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return detailDTOs;
    }

}
