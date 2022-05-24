package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.constant.StatusBill;
import com.dth.spring_boot_shoe.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<BillEntity,Long> {
    BillEntity findByUserIdAndBillType(Long userId,Integer billType);

    List<BillEntity> findByUserIdAndBillTypeAndStatusNot(Long userId, Integer billType, String status);
    List<BillEntity> findByUserIdAndBillTypeAndStatus(Long userId,Integer billType,String status);

    Page<BillEntity> findAllByBillType(Integer billType, Pageable pageable);
    @Query(value = "select * from bill b where b.bill_type=1 and month(b.created_at)=month(now()) and year(b.created_at)=year(now())",
            countQuery = "select count(*) as count from bill b where b.bill_type=1 and month(b.created_at)=month(now()) and year(b.created_at)=year(now())"
            ,nativeQuery = true)
    Page<BillEntity> findAllByBillTypeAndMonthNow(Pageable pageable);

    @Query(value = "select count(*) as count from bill b " +
            "where b.bill_type=1 and month(b.created_at)=month(now()) and year(b.created_at)=year(now())",nativeQuery = true)
    List<Object[]> findByBillTypeAndCreatedAt();

    @Query(value = "select count(b) from BillEntity b " +
            "where b.user.id=:userId and b.billType=1 and b.status <> 'finished'")
    Integer getCountBillByUser(Long userId);
}
