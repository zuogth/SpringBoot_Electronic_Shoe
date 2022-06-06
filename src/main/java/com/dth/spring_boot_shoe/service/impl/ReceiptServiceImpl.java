package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.constant.MessageErr;
import com.dth.spring_boot_shoe.entity.*;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.*;
import com.dth.spring_boot_shoe.request.ProductExportRequest;
import com.dth.spring_boot_shoe.request.ReceiptRequest;
import com.dth.spring_boot_shoe.response.ProductDetailResponse;
import com.dth.spring_boot_shoe.response.ReceiptDetailResponse;
import com.dth.spring_boot_shoe.response.ReceiptResponse;
import com.dth.spring_boot_shoe.service.ImageService;
import com.dth.spring_boot_shoe.service.ReceiptService;
import com.dth.spring_boot_shoe.utils.ExportReport;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final EntityManager entityManager;
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductReceiptRepository productReceiptRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ExportReport exportReport;

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
        ReceiptEntity receipt = receiptRepository.findById(id).orElseThrow(()->new ApiRequestException("Hóa đơn không tồn tại"));
        Pageable pageable=PageRequest.of(page-1,10);
        Page<ProductReceiptEntity> entities=productReceiptRepository.findByReceiptIdAndGroupByProductAndColor(id,pageable);
        map.put("totalItemChs",entities.getTotalElements());
        List<ReceiptDetailResponse> responses=new ArrayList<>();
        entities.forEach(e->responses.add(ReceiptDetailResponse.converter(e,
                imageService.findByColorIdAndProductIdAndParent(e.getProductDetail().getColor().getId(),e.getProductDetail().getProduct().getId()),
                productReceiptRepository.findSumReceiptByReceiptIdAndGroupByColor(id,
                        e.getProductDetail().getColor().getId(),
                        e.getProductDetail().getProduct().getId()))));
        Integer quantity = productReceiptRepository.findQuantityTotal(id);
        map.put("receiptDetails",responses);
        map.put("receipt",receipt);
        map.put("quantity",quantity);
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

    @Transactional
    @Override
    public void insert(ReceiptRequest receiptRequest) {
        UserEntity user=userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),1,true)
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
            ProductDetailEntity productDetail=productDetailRepository.findByIdAndStatus(e.getId(),1)
                    .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
            productReceiptEntity.setProductDetail(productDetail);
            productReceiptEntity.setReceipt(finalReceipt);
            productReceiptEntity.setQuantity(e.getReceipt());
            productReceiptRepository.save(productReceiptEntity);
        });
    }

    @Override
    public Map<String, Object> findDetailsById(Long id) {
        Map<String,Object> map = new HashMap<>();
        ReceiptEntity receipt = receiptRepository.findById(id).orElseThrow(()->new ApiRequestException("Hóa đơn không tồn tại"));
        List<ProductDetailResponse> products = new ArrayList<>();
        List<ProductReceiptEntity> entities = productReceiptRepository.findByReceiptId(receipt.getId());
        entities.forEach(e->products.add(ProductDetailResponse.converter(e.getProductDetail(),null,e.getQuantity(),
                imageService.findByColorIdAndProductIdAndParent(e.getProductDetail().getColor().getId(),e.getProductDetail().getProduct().getId()),null)));
        Integer quantity = productReceiptRepository.findQuantityTotal(receipt.getId());
        ReceiptResponse response = ReceiptResponse.converter(receipt,quantity);
        map.put("receipt",response);
        map.put("quantity",quantity);
        map.put("productDetails",products);
        map.put("colors",colorRepository.findAll());
        map.put("sizes",sizeRepository.findAll());
        map.put("products",productRepository.findByBrandIdAndStatus(receipt.getBrand().getId(),1));
        return map;
    }

    @Override
    public void update(ReceiptRequest receiptRequest) {
        ReceiptEntity receipt = receiptRepository.findById(receiptRequest.getId()).orElseThrow(()-> new ApiRequestException("Hóa đơn này không tồn tại"));
        BrandEntity brand=brandRepository.findById(receiptRequest.getBrandId())
                .orElseThrow(()->new ApiRequestException("Nhà cung cấp không tồn tại!"));
        receipt.setBrand(brand);
        receipt.setTotalprice(receiptRequest.getTotalMoney());
        receiptRepository.save(receipt);
        List<ProductReceiptEntity> entities = productReceiptRepository.findByReceiptId(receipt.getId());
        entities.forEach(e->productReceiptRepository.delete(e));

        receiptRequest.getProductDetails().forEach(e->{
            ProductReceiptEntity productReceiptEntity=new ProductReceiptEntity();
            ProductDetailEntity productDetail=productDetailRepository.findByIdAndStatus(e.getId(),1)
                    .orElseThrow(()->new ApiRequestException(MessageErr.NOT_FOUND));
            productReceiptEntity.setProductDetail(productDetail);
            productReceiptEntity.setReceipt(receipt);
            productReceiptEntity.setQuantity(e.getReceipt());
            productReceiptRepository.save(productReceiptEntity);
        });
    }

    @Override
    public String ExportReceipt(Long id, String type) {
        UserEntity user = userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),1,true)
                .orElseThrow(()-> new ApiRequestException("Bạn chưa đăng nhập"));
        ReceiptEntity receipt = receiptRepository.findById(id).orElseThrow(()-> new ApiRequestException("Hóa đơn không tồn tại"));
        List<ProductReceiptEntity> entities = productReceiptRepository.findByReceiptIdGroupByProduct(receipt.getId());
        List<ProductExportRequest> exportRequests = new ArrayList<>();
        AtomicReference<Integer> index = new AtomicReference<>(0);
        entities.forEach(e->{
            index.updateAndGet(v -> v + 1);
            Integer quantity = productReceiptRepository.findSumQuantityEachProductByReceipt(e.getProductDetail().getProduct().getId(),receipt.getId());
            exportRequests.add(ProductExportRequest.converter(index.get(),e,quantity));
        });
        String str = "";
        try {
            // Ten template
            String templateName = "Receipt_report.jrxml";
            // Tao du lieu dataSources
            Map<String, Object> dataSources = new HashMap<>();
            JRBeanCollectionDataSource dataTable = new JRBeanCollectionDataSource(exportRequests, false);
            dataSources.put("ListData", dataTable);
            // Tao params
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_date", String.valueOf(LocalDate.now().getDayOfMonth()));
            parameters.put("p_month", String.valueOf(LocalDate.now().getMonthValue()));
            parameters.put("p_year", String.valueOf(LocalDate.now().getYear()));
            parameters.put("p_fullName", user.getFullName());
            parameters.put("p_brand", receipt.getBrand().getName());
            parameters.put("p_receiptDate",receipt.getCreatedAt().toLocalDate().toString());
            parameters.put("p_totalPrice",receipt.getTotalprice());
            parameters.put("p_totalPriceVN", StringUtils.convertMoneyToString(receipt.getTotalprice().longValue()));
            // Xuat bao cao
            if(type.equals("pdf")){
                str = exportReport.createPdfReport(templateName, dataSources, parameters);
            }else if(type.equals("word")){
                str = exportReport.createWordReport(templateName, dataSources, parameters);
            }else {
                str = exportReport.createExcelReport(templateName, dataSources, parameters);
            }
            System.out.println("Xuat bao cao thanh cong");
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Xuat bao cao that bai");
        }

        return str;
    }
}
