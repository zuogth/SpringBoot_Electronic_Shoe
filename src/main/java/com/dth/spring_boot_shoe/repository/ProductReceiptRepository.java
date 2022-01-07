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
}
