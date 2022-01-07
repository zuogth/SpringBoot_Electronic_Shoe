package com.dth.spring_boot_shoe.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckQuantity {
    private SizeQuantity sizeQuantity;
    private Boolean outQuantity;
}
