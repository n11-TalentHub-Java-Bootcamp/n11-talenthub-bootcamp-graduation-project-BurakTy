package com.buraktuysuz.n11.finalproject.converter;

import com.buraktuysuz.n11.finalproject.entity.concretes.CreditApplication;
import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationFormDto;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationResultDto;
import com.buraktuysuz.n11.finalproject.entity.dtos.CustomerRequestDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer convertCustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto);

    @Mapping(target = "creditScore",source = ".",qualifiedByName = "setCreditScoreZero")
    Customer convertCreditApplicationFormDtoToCustomer(CreditApplicationFormDto customerRequestDto);


    @Named("setCreditScoreZero")
    default int setCreditScoreZero(CreditApplicationFormDto formDto) {
        return 0;
    }
}
