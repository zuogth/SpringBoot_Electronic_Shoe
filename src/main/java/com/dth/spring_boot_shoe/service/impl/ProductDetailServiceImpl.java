package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.entity.*;
import com.dth.spring_boot_shoe.exception.ApiHandleException;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ProductDetail;
import com.dth.spring_boot_shoe.response.SoldReceiptProdDetail;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.ProductDetailService;
import com.dth.spring_boot_shoe.service.SizeService;
import com.dth.spring_boot_shoe.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductDetailRepository productDetailRepository;
    private final HttpServletRequest request;
    private final ImageRepository imageRepository;
    private final ProductBillRepository productBillRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final ImageService imageService;
    private final SizeService sizeService;


    @Value("${image.location}")
    private String location;
    @Override
    public Long insert(ProductDetail productDetail) {
        ProductEntity product=productRepository.findByIdAndStatusNot(productDetail.getProductId(),-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm không tồn tại"));
        ColorEntity color=colorRepository.findById(productDetail.getColorId())
                        .orElseThrow(()->new ApiRequestException("Màu này không tồn tại"));
        productDetail.getSizeIds().forEach(sizeId->{
            ProductDetailEntity entity=new ProductDetailEntity();
            entity.setProduct(product);
            entity.setColor(color);
            SizeEntity size=sizeRepository.findById(sizeId).orElseThrow(()->new ApiRequestException("Size này không tồn tại"));
            entity.setSize(size);
            entity.setStatus(1);
            productDetailRepository.save(entity);

        });
        //insert images detail
        productDetail.getFileChr().forEach(file->{
            try {
                ImageEntity image=new ImageEntity();
                image.setProductId(productDetail.getProductId());
                image.setColorId(productDetail.getColorId());
                image.setImage(FileUtils.setImage(file,this.location,request));
                image.setParent(0);
                imageRepository.save(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //insert img parent
        ImageEntity imagePr=new ImageEntity();
        imagePr.setProductId(productDetail.getProductId());
        imagePr.setColorId(productDetail.getColorId());
        try {
            imagePr.setImage(FileUtils.setImage(productDetail.getFile(),this.location,request));
            imagePr.setParent(1);
            imageRepository.save(imagePr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productDetail.getProductId();
    }

    @Override
    public void insertSize(ProductDetail productDetail, Long sizeId) {
        ProductEntity product=productRepository.findByIdAndStatusNot(productDetail.getProductId(),-1)
                .orElseThrow(()-> new ApiRequestException("Không tìm thấy loại giày này!"));
        ColorEntity color=colorRepository.findById(productDetail.getColorId())
                .orElseThrow(()->new ApiRequestException("Màu này không được tìm thấy!"));
        SizeEntity size=sizeRepository.findById(sizeId)
                .orElseThrow(()->new ApiRequestException("Cỡ này không tồn tại!"));
        ProductDetailEntity entity=new ProductDetailEntity();
        entity.setProduct(product);
        entity.setColor(color);
        entity.setSize(size);
        entity.setStatus(1);
        productDetailRepository.save(entity);
    }

    @Override
    public Map<String,Object> findById(Long id) {
        Map<String,Object> map=new HashMap<>();
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm không được tìm thấy"));
        map.put("productDetail",com.dth.spring_boot_shoe.response.ProductDetail.converter(entity,
                        Integer.valueOf(productBillRepository.findSumProductDetailSold(entity.getProduct().getId(),entity.getColor().getId()).get(0)[0].toString()),
                        Integer.valueOf(productReceiptRepository.findSumProductDetailReceipt(entity.getProduct().getId(),entity.getColor().getId()).get(0)[0].toString()),
                        imageService.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId()),
                        imageService.findByColorAndProductAndParentNot(entity.getColor().getId(),entity.getProduct().getId())));
        map.put("colors",colorRepository.findColorByNotInProductDetailAndIt(id,entity.getProduct().getId()));
        map.put("sizes",sizeService.findSizeNotInProductDetail(entity.getProduct().getId(),entity.getColor().getId()));
        return map;
    }

    @Override
    public void update(ProductDetail productDetail) throws IOException {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(productDetail.getId(),-1)
                .orElseThrow(()->new ApiRequestException("San pham khong duoc tim thay"));
        if(productDetail.getColorId()!=entity.getColor().getId()){//nếu có thay đổi màu sác thì update
            List<ProductDetailEntity> list=
                    productDetailRepository.findByProductIdAndColorIdAndStatusNot(entity.getProduct().getId(),
                            entity.getColor().getId(),-1);
            ColorEntity color= colorRepository.findById(productDetail.getColorId())
                    .orElseThrow(()->new ApiRequestException("Mau sac nay khong hop le"));
            list.forEach(e->{
                e.setColor(color);
                productDetailRepository.save(e);
            });
        }
        if(!Objects.equals(productDetail.getFile().getOriginalFilename(), "")){//nếu dổi ảnh chính cho sản phẩm
            Optional<ImageEntity> image=imageRepository.findByColorIdAndProductIdAndParent(entity.getColor().getId(),entity.getProduct().getId(),1);
            ImageEntity imagePr=new ImageEntity();
            if(image.isPresent()){
                imagePr=image.get();
                imagePr.setImage(FileUtils.setImage(productDetail.getFile(),location,request));
            }else {
                imagePr.setProductId(entity.getProduct().getId());
                imagePr.setColorId(entity.getColor().getId());
                imagePr.setImage(FileUtils.setImage(productDetail.getFile(),this.location,request));
                imagePr.setParent(1);
            }
            imageRepository.save(imagePr);
        }
        if(!Objects.equals(productDetail.getFileChr().get(0).getOriginalFilename(), "")){
            List<ImageEntity> entities=imageRepository.findByColorIdAndProductIdAndParentNot(entity.getColor().getId(),entity.getProduct().getId(),1);
            imageRepository.deleteAll(entities);
            productDetail.getFileChr().forEach(e->{
                try {
                    ImageEntity image=new ImageEntity();
                    image.setProductId(entity.getProduct().getId());
                    image.setColorId(entity.getColor().getId());
                    image.setImage(FileUtils.setImage(e,this.location,request));
                    image.setParent(0);
                    imageRepository.save(image);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    @Override
    public void updateSizeForProductDetail(Long id, Long sizeId) {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm không được tìm thấy!"));
        SizeEntity size=sizeRepository.findById(sizeId)
                        .orElseThrow(()->new ApiRequestException("Cỡ này không tồn tại!"));
        entity.setSize(size);
        productDetailRepository.save(entity);
    }

    @Override
    public void lock(Long id, Integer status) {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException("Sản phẩm không tồn tại!"));
        entity.setStatus(status);
        productDetailRepository.save(entity);
    }
}
