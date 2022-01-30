package com.buraktuysuz.n11.finalproject.entity.dtos;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditApplicationFormDto extends CustomerRequestDto {
    private BigDecimal deposit;
}
