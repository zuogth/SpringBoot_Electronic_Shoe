package com.dth.spring_boot_shoe.repository;

import com.dth.spring_boot_shoe.constant.StatusBill;
import com.dth.spring_boot_shoe.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<BillEntity,Long> {
    BillEntity findByUserIdAndBillType(Long userId,Integer billType);

    List<BillEntity> findByUserIdAndBillTypeAndStatusNot(Long userId, Integer billType, String status);
    List<BillEntity> findByUserIdAndBillTypeAndStatus(Long userId,Integer billType,String status);
}
