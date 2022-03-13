package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ProductBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductBillRepository extends JpaRepository<ProductBillEntity,Long> {
    ProductBillEntity findByBillIdAndProductDetailId(Long billId,Long prodDetailId);

    List<ProductBillEntity> findByBillId(Long billId);
    //Sum product is sold
    @Query(value = "select if(sum(quantity) is null,0,sum(quantity)) sold from product_detail pb left join " +
            "(select pb.product_detail_id,pb.quantity " +
            "from product_bill pb join bill on bill.id=pb.bill_id " +
            "where bill.bill_type=1 or bill.paying=1) as a on pb.id=a.product_detail_id " +
            "where pb.product_id=:product_id",nativeQuery = true)
    List<Object[]> findSumProductSold(@Param("product_id") Long product_id);

    //sum product detail sold

    @Query(value = "select if(sum(quantity) is null,0,sum(quantity)) sold from product_detail pd left join " +
            "(select pb.product_detail_id,pb.quantity " +
            "from product_bill pb join bill on bill.id=pb.bill_id " +
            "where bill.bill_type=1 or bill.paying=1) as a on pd.id=a.product_detail_id " +
            "where pd.product_id=:product_id and pd.color_id=:color_id",nativeQuery = true)
    List<Object[]> findSumProductDetailSold(@Param("product_id") Long product_id,
                                            @Param("color_id") Long color_id);

    //sum sold by each product detail

    @Query(value = "select if(sum(quantity) is null,0,sum(quantity)) sold from product_bill " +
            "where product_detail_id=:id",nativeQuery = true)
    List<Object[]> findSumSoldByEachProductDetail(@Param("id") Long id);
}
