package com.dth.spring_boot_shoe.dto;

import com.dth.spring_boot_shoe.constant.StatusBill;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BillDTO{

    private List<BillReceiptDTO> dtos;
    private BigDecimal totalPrice;
    private String status;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
