package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.dto.ProductDetail;
import com.dth.spring_boot_shoe.dto.ProductDetailDTO;
import com.dth.spring_boot_shoe.entity.*;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.exception.RequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.DiscountRequest;
import com.dth.spring_boot_shoe.request.EventRequest;
import com.dth.spring_boot_shoe.response.EventResponse;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.response.ProductDiscountResponse;
import com.dth.spring_boot_shoe.service.EventService;
import com.dth.spring_boot_shoe.service.FirebaseService;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EntityManager entityManager;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final BrandRepository brandRepository;
    private final DiscountRepository discountRepository;
    private final FirebaseService firebaseService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;


    @Override
    public Map<String, Object> getAll(int page) {
        Pageable pageable= PageRequest.of(page-1,10);
        Page<EventEntity> entities= eventRepository.findAll(pageable);

        Map<String,Object> map=new HashMap<>();
        map.put("totalItems",entities.getTotalElements());
        map.put("events",entities.getContent());
        return map;
    }

    @Override
    public List<?> getProducts(String type,Long id) {
        return type.equals("brand")?productRepository.findByBrandIdAndStatus(id,1):productDetailRepository.findByProductIdAndStatus(id,1);
    }

    @Override
    public EventEntity insert(EventRequest discount) {
        EventEntity eventEntity =new EventEntity();
        eventEntity.setName(discount.getName());
        eventEntity.setSlug(StringUtils.removeAccent(discount.getName()));
        eventEntity.setStartAt(LocalDateTime.parse(discount.getStartAt()));
        eventEntity.setEndAt(LocalDateTime.parse(discount.getEndAt()));
        eventEntity.setDescription(discount.getDescription());
        eventEntity.setStyle(discount.getStyle());
        Integer show=discount.getShow()==null?0:discount.getShow();
        eventEntity.setShowWeb(show);
        try {
            String banner=firebaseService.upLoadFile(discount.getBanner(),"banner");
            String url=firebaseService.getUrl(banner,"banner");
            eventEntity.setBanner(banner);
            eventEntity.setUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventEntity event = eventRepository.save(eventEntity);
        if(discount != null){
            discount.getDiscounts().forEach(dis->{
                DiscountEntity discountEntity = new DiscountEntity();
                discountEntity.setDiscount(dis);
                discountEntity.setEvent(event);
                discountRepository.save(discountEntity);
            });
        }
        return event;
    }

    @Override
    public void update(EventRequest discount) {
        EventEntity eventEntity = eventRepository.findById(discount.getId()).orElseThrow(()->new ApiRequestException("Sự kiện không tồn tại"));
        eventEntity.setName(discount.getName());
        eventEntity.setSlug(StringUtils.removeAccent(discount.getName()));
        eventEntity.setStartAt(LocalDateTime.parse(discount.getStartAt()));
        eventEntity.setEndAt(LocalDateTime.parse(discount.getEndAt()));
        eventEntity.setDescription(discount.getDescription());
        eventEntity.setStyle(discount.getStyle());
        Integer show=discount.getShow()==null?0:discount.getShow();
        eventEntity.setShowWeb(show);
        try {
            firebaseService.deleteImage(eventEntity.getBanner(),"banner");
            String banner=firebaseService.upLoadFile(discount.getBanner(),"banner");
            String url=firebaseService.getUrl(banner,"banner");
            eventEntity.setBanner(banner);
            eventEntity.setUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventRepository.save(eventEntity);
    }

    @Override
    public Map<String,Object> getById(Long id) {
        Map<String,Object> map = new HashMap<>();
        EventEntity event = eventRepository.findById(id).orElseThrow(()-> new ApiRequestException("Sự kiện không tồn tại"));
        List<DiscountEntity> discounts =  discountRepository.findByEventId(event.getId());
        List<ProductDiscountResponse> responses = new ArrayList<>();
        if(discounts != null){
            discounts.forEach(dis->{
                List<ProductDetailEntity> detailEntities = productDetailRepository.findAllByDiscountIdAndStatus(dis.getId());
                detailEntities.forEach(detail->responses.add(ProductDiscountResponse.converter(detail,
                        imageService.findByColorIdAndProductIdAndParent(detail.getColor().getId(),detail.getProduct().getId()),dis.getDiscount())));
            });
        }
        map.put("event",EventResponse.converter(event,discounts));
        map.put("products",responses);
        return map;
    }

    @Override
    public List<ProductDetailResponse> findProductDetails(Long brandId, Long productId) {
        List<ProductDetailEntity> detailEntities;
        List<ProductDetailResponse> responses=new ArrayList<>();
        TypedQuery<ProductDetailEntity> typedQuery;
        String query="select pd from ProductDetailEntity pd where pd.status = 1 and pd.discountId=0 ";
        if(brandId!=null){
            query+="and pd.product.brand.id="+brandId;
        }
        if(productId!=null){
            query+="and pd.product.id="+productId;
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
    public void insertDis(DiscountRequest[] requests) {
        Arrays.stream(requests).forEach(dis->updateProduct(dis.getProductDetailIds(),dis.getDiscountId()));
    }

    @Override
    public List<ProductDetail> findProductSale(String slugEvent) {
        EventEntity event = eventRepository.findBySlug(slugEvent).orElseThrow(()->new RequestException("Sự kiện đang cập nhật!","/"));
        List<DiscountEntity> discounts = discountRepository.findByEventId(event.getId());
        List<ProductDetail> details = new ArrayList<>();
        discounts.forEach(dis->{
            List<ProductDetailEntity> detailEntities = productDetailRepository.findByDiscountIdAndStatus(dis.getId());
            detailEntities.forEach(e->details.add(ProductDetail.converter(e,imageService.findByColorIdAndProductIdAndParent(e.getColor().getId(),e.getProduct().getId()),dis.getDiscount())));
        });
        return details;
    }

    @Override
    public List<ProductDetail> findAllProductsSale() {
        List<ProductDetail> details =new ArrayList<>();
        List<EventEntity> events = eventRepository.findByEndAtAfter(LocalDateTime.now());
        events.forEach(event -> {
            List<DiscountEntity> discounts = discountRepository.findByEventId(event.getId());
            discounts.forEach(dis->{
                List<ProductDetailEntity> detailEntities = productDetailRepository.findByDiscountIdAndStatus(dis.getId());
                detailEntities.forEach(prod->details.add(ProductDetail.converter(prod,imageService.findByColorIdAndProductIdAndParent(prod.getColor().getId(),prod.getProduct().getId()),dis.getDiscount())));
            });
        });
        return details;
    }

    @Override
    public void delete(Long id) {
        EventEntity event = eventRepository.findById(id).orElseThrow(()->new ApiRequestException("Sự kiện này không tồn tại"));
        List<DiscountEntity> discounts = discountRepository.findByEventId(event.getId());
        discounts.forEach(dis->{
            List<ProductDetailEntity> productDetails = productDetailRepository.findAllByDiscountIdAndStatus(dis.getId());
            productDetails.forEach(pro->{
                pro.setDiscountId(0L);
                productDetailRepository.save(pro);
            });
            discountRepository.delete(dis);
        });
        firebaseService.deleteImage(event.getBanner(),"banner");
        eventRepository.delete(event);
    }

    @Override
    public void updateShow(Long id, Integer showWeb) {
        EventEntity event =eventRepository.findById(id).orElseThrow(()-> new ApiRequestException("Sự kiện không tồn tại"));
        event.setShowWeb(showWeb);
        eventRepository.save(event);
    }

    private void updateProduct(List<Long> productDetailIds,Long discountId){
        if(productDetailIds != null){
            productDetailIds.forEach(id->{
                ProductDetailEntity product=productDetailRepository.findByIdAndStatus(id,1).orElseThrow(()->new ApiRequestException("Sản phẩm có ID "+id+" không tồn tại"));
                product.setDiscountId(discountId);
                productDetailRepository.save(product);
            });
        }
    }
}
