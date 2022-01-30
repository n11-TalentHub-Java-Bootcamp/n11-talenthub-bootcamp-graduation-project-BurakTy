package com.buraktuysuz.n11.finalproject.entity.dtos;

import com.buraktuysuz.n11.finalproject.utility.enums.EnumApplicationResult;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditApplicationResultDto {
    private String fullName;
    private BigDecimal creditLimit;
    private EnumApplicationResult enumApplicationResult;
    private Date applicationTime;
    private String description;
}
