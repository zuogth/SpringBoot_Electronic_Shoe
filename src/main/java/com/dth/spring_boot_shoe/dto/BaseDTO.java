package com.dth.spring_boot_shoe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseDTO {
    private Long id;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
