package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.constant.MessageAdmin;
import com.dth.spring_boot_shoe.constant.MessageErr;
import com.dth.spring_boot_shoe.entity.*;
import com.dth.spring_boot_shoe.exception.ApiException;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ProductDetail;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.service.FirebaseService;
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
    private final FirebaseService firebaseService;


    @Value("${image.location}")
    private String location;
    @Override
    public Long insert(ProductDetail productDetail) {
        ProductEntity product=productRepository.findByIdAndStatusNot(productDetail.getProductId(),-1)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        ColorEntity color=colorRepository.findById(productDetail.getColorId())
                        .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_COLOR));
        productDetail.getSizeIds().forEach(sizeId->{
            ProductDetailEntity entity=new ProductDetailEntity();
            entity.setProduct(product);
            entity.setColor(color);
            SizeEntity size=sizeRepository.findById(sizeId).orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_SIZE));
            entity.setSize(size);
            entity.setStatus(1);
            productDetailRepository.save(entity);

        });
        //insert images detail
        productDetail.getFileChr().forEach(file->{
            try {
                ImageEntity image=new ImageEntity();
                String imageName=firebaseService.upLoadFile(file,"shoe");
                String url=firebaseService.getUrl(imageName,"shoe");
                image.setProductId(productDetail.getProductId());
                image.setColorId(productDetail.getColorId());
                image.setImage(imageName);
                image.setUrl(url);
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
            String imageName=firebaseService.upLoadFile(productDetail.getFile(),"shoe");
            String url=firebaseService.getUrl(imageName,"shoe");
            imagePr.setImage(imageName);
            imagePr.setUrl(url);
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
                .orElseThrow(()-> new ApiRequestException(MessageErr.NOT_FOUND));
        ColorEntity color=colorRepository.findById(productDetail.getColorId())
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_COLOR));
        SizeEntity size=sizeRepository.findById(sizeId)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_SIZE));
        ProductDetailEntity entity=new ProductDetailEntity();
        entity.setProduct(product);
        entity.setColor(color);
        entity.setSize(size);
        entity.setStatus(1);
        productDetailRepository.save(entity);
    }

    @Override
    public Map<String,Object> findById(Long productId,Long colorId) {
        Map<String,Object> map=new HashMap<>();
        ProductDetailEntity entity=productDetailRepository.findTopByProductIdAndColorIdAndStatusNot(productId,colorId,-1)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        map.put("productDetail", ProductDetailResponse.converter(entity,
                        productBillRepository.findSumProductDetailSold(productId,colorId),
                        productReceiptRepository.findSumProductDetailReceipt(productId,colorId),
                        imageService.findByColorIdAndProductIdAndParent(colorId,productId),
                        imageService.findByColorAndProductAndParentNot(colorId,productId)));
        map.put("colors",colorRepository.findColorByNotInProductDetailAndIt(entity.getId(),productId));
        map.put("sizes",sizeService.findSizeNotInProductDetail(productId,entity.getColor().getId()));
        return map;
    }

    @Override
    public void update(ProductDetail productDetail) throws IOException {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(productDetail.getId(),-1)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        if(productDetail.getColorId()!=entity.getColor().getId()){//nếu có thay đổi màu sác thì update
            List<ProductDetailEntity> list=
                    productDetailRepository.findByProductIdAndColorIdAndStatusNot(entity.getProduct().getId(),
                            entity.getColor().getId(),-1);
            ColorEntity color= colorRepository.findById(productDetail.getColorId())
                    .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_COLOR));
            List<ImageEntity> imageEntities=imageRepository.findByColorIdAndProductId(entity.getColor().getId(),entity.getProduct().getId());
            imageEntities.forEach(e->{
                e.setColorId(color.getId());
                imageRepository.save(e);
            });
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
                firebaseService.deleteImage(imagePr.getImage(),"shoe");
                String imageName=firebaseService.upLoadFile(productDetail.getFile(),"shoe");
                String url=firebaseService.getUrl(imageName,"shoe");
                imagePr.setImage(imageName);
                imagePr.setUrl(url);
            }else {
                String imageName=firebaseService.upLoadFile(productDetail.getFile(),"shoe");
                String url=firebaseService.getUrl(imageName,"shoe");
                imagePr.setProductId(entity.getProduct().getId());
                imagePr.setColorId(entity.getColor().getId());
                imagePr.setImage(imageName);
                imagePr.setUrl(url);
                imagePr.setParent(1);
            }
            imageRepository.save(imagePr);
        }
        if(!Objects.equals(productDetail.getFileChr().get(0).getOriginalFilename(), "")){
            List<ImageEntity> entities=imageRepository.findByColorIdAndProductIdAndParentNot(entity.getColor().getId(),entity.getProduct().getId(),1);
            entities.forEach(e->firebaseService.deleteImage(e.getImage(),"shoe"));
            imageRepository.deleteAll(entities);
            productDetail.getFileChr().forEach(e->{
                try {
                    ImageEntity image=new ImageEntity();
                    String imageName=firebaseService.upLoadFile(e,"shoe");
                    String url=firebaseService.getUrl(imageName,"shoe");
                    image.setProductId(entity.getProduct().getId());
                    image.setColorId(entity.getColor().getId());
                    image.setImage(imageName);
                    image.setUrl(url);
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
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        SizeEntity size=sizeRepository.findById(sizeId)
                        .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND_SIZE));
        entity.setSize(size);
        productDetailRepository.save(entity);
    }

    @Override
    public void lock(Long id, Integer status) {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        entity.setStatus(status);
        productDetailRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        List<ProductDetailEntity> list=productDetailRepository.findByProductIdAndColorIdAndStatusNot(entity.getProduct().getId(),
                entity.getColor().getId(),-1);
        list.forEach(e->{
            e.setStatus(-1);
            productDetailRepository.save(e);
        });
    }

    @Override
    public ApiException deleteSingle(Long id) {
        ProductDetailEntity entity=productDetailRepository.findByIdAndStatusNot(id,-1)
                .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
        entity.setStatus(-1);
        productDetailRepository.save(entity);
        int count=productDetailRepository.findByProductIdAndColorIdAndStatusNot(entity.getProduct().getId(),entity.getColor().getId(),-1)
                .size();
        return count>0? MessageAdmin.DELETED_SUCCESS:MessageAdmin.ZERO;
    }
}
