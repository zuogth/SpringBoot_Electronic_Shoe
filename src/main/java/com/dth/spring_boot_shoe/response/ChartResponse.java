package com.dth.spring_boot_shoe.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ChartResponse {
    private String label;
    private String backgroundColor;
    private String borderColor;
    private List<BigDecimal> data;

}
