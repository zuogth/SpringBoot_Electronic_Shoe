package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.entity.ProductReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductReceiptRepository extends JpaRepository<ProductReceiptEntity,Long> {

    @Query(value = "select b.id,s.name, b.count from size s join " +
            "(select pd.*,if(a.count is null,0,a.count)-if(sum(pbb.quantity) is null,0,sum(pbb.quantity)) as count " +
            "from product_detail pd left join (select pb.* " +
            "from product_bill pb join bill on bill.id=pb.bill_id " +
            "where bill.bill_type=1 or bill.paying=1) as pbb on pbb.product_detail_id=pd.id join " +
            "(select pd.id,sum(pr.quantity) as count " +
            "from product_detail pd left join product_receipt pr on pd.id=pr.product_detail_id " +
            "group by pd.id) as a on a.id=pd.id " +
            "where pd.id=:prod_detail_id) as b on s.id=b.size_id",nativeQuery = true)
    List<Object[]> findByProductIdGroupBySizeId(@Param("prod_detail_id") Long prod_detail_id);

    @Query(value = "select sum(b.count) count from " +
            "(select pd.*,if(prb.count is null,0,prb.count)-if(sum(pbb.quantity) is null,0,sum(pbb.quantity)) as count " +
            "from product_detail pd left join (select pb.product_detail_id,pb.quantity " +
            "from product_bill pb join bill on bill.id=pb.bill_id " +
            "where bill.bill_type=1 or bill.paying=1) as pbb on pbb.product_detail_id=pd.id join " +
            "(select pd.id, pd.product_id,sum(pr.quantity) as count " +
            "from product_detail pd left join product_receipt pr on pd.id=pr.product_detail_id " +
            "group by pd.id) as prb on prb.id=pd.id " +
            "group by pd.id) as b " +
            "where b.product_id=:product_id and color_id=:color_id " +
            "group by b.product_id,b.color_id",nativeQuery = true)
    List<Object[]> findQuantityByProductIdAndColorId(@Param("product_id") Long product_id,
                                                     @Param("color_id") Long color_id);

    //sum product receipt
    @Query(value = "select if(sum(pr.quantity) is null,0,sum(pr.quantity)) receipt " +
            "from product_detail pd left join product_receipt pr on pd.id=pr.product_detail_id " +
            "where pd.product_id=:product_id",nativeQuery = true)
    List<Object[]> findSumProductReceipt(@Param("product_id") Long product_id);

    //sum product detail receipt
    @Query(value = "select if(sum(pr.quantity) is null,0,sum(pr.quantity)) receipt " +
            "from product_detail pd left join product_receipt pr on pd.id=pr.product_detail_id " +
            "where pd.product_id=:product_id and pd.color_id=:color_id",nativeQuery = true)
    List<Object[]> findSumProductDetailReceipt(@Param("product_id") Long product_id,
                                               @Param("color_id") Long color_id);

    //sum receipt by each product detail
    @Query(value = "select if(sum(quantity) is null,0,sum(quantity)) receipt from product_receipt " +
            "where product_detail_id=:id",nativeQuery = true)
    List<Object[]> findSumReceiptByEachProductDetail(@Param("id") Long id);
}
