package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.BrandEntity;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ProductFilter;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.response.Product;
import com.dth.spring_boot_shoe.response.ProductDetail;
import com.dth.spring_boot_shoe.response.SizeQuantity;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.ProductService;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceEmpl implements ProductService {

    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final ProductBillRepository productBillRepository;
    private final BrandRepository brandRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductEntity> findByBrandId(Long brand_id) {
        Pageable pageable=PageRequest.of(0,10);
        return productRepository.findAllByBrandIdAndStatusNot(brand_id,-1,pageable).getContent();
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
        Optional<ProductDetailEntity> prod= productDetailRepository.findByIdAndStatusNot(id,-1);
        return ProductDetailDTO.converter(modelMapper,prod.get(),imageService.findByColorIdAndProductIdAndParent(prod.get().getColor().getId(),prod.get().getProduct().getId()));
    }

    @Override
    public List<SizeQuantity> findAllSizeByProductId(Long id, Long color_id) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findByProductIdAndColorIdAndStatusNot(id,color_id,-1);
        List<SizeQuantity> detailDTOS=new ArrayList<>();
        detailEntities.forEach(entity->{
            List<Object[]> objects=productReceiptRepository.findByProductIdGroupBySizeId(entity.getId());
            detailDTOS.add(SizeQuantity.converter(objects.get(0)));
        });
        return detailDTOS;
    }

    @Override
    public List<ProductDetailDTO> findSameProduct(Long product_id) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findByProductIdGroupByProductIdAndColorId(product_id);
        List<ProductDetailDTO> detailDTOs=new ArrayList<>();
        detailEntities.forEach(entity->detailDTOs.add(ProductDetailDTO.converter(modelMapper,entity,imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return detailDTOs;
    }

    @Override
    public List<ProductDetailDTO> findBySlug(String slug) {
        List<ProductDetailEntity> detailEntities=productDetailRepository.findBySlugProduct(slug);
        List<ProductDetailDTO> detailDTOs=new ArrayList<>();
        detailEntities.forEach(entity->detailDTOs.add(ProductDetailDTO.converter(modelMapper,entity,imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return detailDTOs;
    }

    //filter product by options with color null
    @Override
    public List<ProductEntity> findProductByBrandAndSize(ProductFilter productFilter) {
        List<ProductEntity> entities;
        TypedQuery<ProductEntity> typedQuery;
        String query;
        String querySort="";
        String queryPrice="";
        if(!Objects.equals(productFilter.getPrice(), "")){
            queryPrice=" and pd.product.price "+productFilter.getPrice();
        }
        if(!Objects.equals(productFilter.getSort(), "")){
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

    //filter product by options
    @Override
    public List<ProductDetailDTO> findByBrandAndOptions(ProductFilter productFilter) {
        List<ProductDetailEntity> detailEntities;
        List<ProductDetailDTO> detailDTOs=new ArrayList<>();
        TypedQuery<ProductDetailEntity> typedQuery;
        String query;
        String querySort="";
        String queryPrice="";
        if(!Objects.equals(productFilter.getPrice(), "")){
            queryPrice=" and pd.product.price "+productFilter.getPrice();
        }
        if(!Objects.equals(productFilter.getSort(), "")){
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
        detailEntities.forEach(entity->detailDTOs.add(ProductDetailDTO.converter(modelMapper,entity,
                        imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()))));
        return detailDTOs;
    }

    //get list product parent
    @Override
    public Map<String,Object> findAllByBrand(Long brand_id, int page) {
        Map<String,Object> map=new HashMap<>();
        Pageable pageable= PageRequest.of(page-1,10);
        Page<ProductEntity> entities=
                brand_id==null?productRepository.findByStatusNot(-1,pageable):
                        productRepository.findAllByBrandIdAndStatusNot(brand_id,-1,pageable);
        map.put("totalItems",entities.getTotalElements());
        List<Product> list=new ArrayList<>();
        entities.forEach(entity->
            list.add(Product.converter(entity,null,
                    Integer.valueOf(productBillRepository.findSumProductSold(entity.getId()).get(0)[0].toString()),
                    Integer.valueOf(productReceiptRepository.findSumProductReceipt(entity.getId()).get(0)[0].toString())))
        );
        map.put("products",list);
        return map;
    }

    // get product by id and list product detail
    @Override
    public Map<String,Object> findAllDetailByProduct(Long product_id,int page) {
        Map<String,Object> map=new HashMap<>();
        ProductEntity product=productRepository.findByIdAndStatusNot(product_id,-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm không được tìm thấy"));
        List<ProductDetail> detailList=new ArrayList<>();
        Pageable pageable=PageRequest.of(page-1,5);
        Page<ProductDetailEntity> entities=productDetailRepository.findByProductIdGroupByColor(product_id,pageable);
        entities.forEach(e->
                detailList.add(ProductDetail.converter(e,
                        Integer.valueOf(productBillRepository.findSumProductDetailSold(e.getProduct().getId(),e.getColor().getId()).get(0)[0].toString()),
                        Integer.valueOf(productReceiptRepository.findSumProductDetailReceipt(e.getProduct().getId(),e.getColor().getId()).get(0)[0].toString()),
                        imageService.findByColorIdAndProductIdAndParent(e.getColor().getId(),e.getProduct().getId()),null)));
        map.put("totalItems",entities.getTotalElements());
        map.put("product",Product.converter(product,detailList,0,0));
        return map;
    }

    @Override
    public Product getById(Long id) {
        ProductEntity entity=productRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException("Không tìm thấy sản phẩm có id:"+id));
        return Product.converter(entity,null,0,0);
    }

    @Override
    public void update(Product product) {
        ProductEntity entity=productRepository.findByIdAndStatusNot(product.getId(),-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm không tồn tại"));
        entity=modelMapper.map(product,ProductEntity.class);
        BrandEntity brand=brandRepository.findById(Long.valueOf(product.getBrand()))
                .orElseThrow(()->new ApiRequestException("Loại giày không tồn tại"));
        entity.setBrand(brand);
        entity.setSlug(StringUtils.removeAccent(product.getName()));
        entity.setStatus(1);
        productRepository.save(entity);
    }

    @Override
    public ProductEntity insert(Product product) {
        try{
            ProductEntity entity=modelMapper.map(product,ProductEntity.class);
            BrandEntity brand=brandRepository.findById(Long.valueOf(product.getBrand()))
                    .orElseThrow(()->new ApiRequestException("Loại giày không tồn tại"));
            entity.setBrand(brand);
            entity.setSlug(StringUtils.removeAccent(product.getName()));
            entity.setStatus(1);
            return productRepository.save(entity);
        }catch (Exception ignored){
            throw new ApiRequestException("Thêm mới sản phẩm không thành công");
        }
    }

    @Override
    public void lockProd(Long id, Integer status) {
        ProductEntity product=productRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm này không được tìm thấy"));
        if(status==0){
            List<ProductDetailEntity> detailEntities=productDetailRepository.findByProductIdAndStatusNot(id,-1);
            detailEntities.forEach(e->{
                e.setStatus(0);
                productDetailRepository.save(e);
            });
        }
        product.setStatus(status);
        productRepository.save(product);
    }

}
