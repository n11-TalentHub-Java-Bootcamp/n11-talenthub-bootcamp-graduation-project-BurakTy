package com.buraktuysuz.n11.finalproject.converter;

import com.buraktuysuz.n11.finalproject.entity.concretes.CreditApplication;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditApplicationMapper {

    CreditApplicationMapper INSTANCE = Mappers.getMapper(CreditApplicationMapper.class);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "toFullName")
    CreditApplicationResultDto convertToCreditApplicationResultDto(CreditApplication creditApplication);
    @Mapping(target = "fullName", source = ".", qualifiedByName = "toFullName")
    List<CreditApplicationResultDto> convertToCreditApplicationResultListDto(List<CreditApplication> creditApplication);


    @Named("toFullName")
    default String translateToFullName(CreditApplication creditApplication) {
        return creditApplication.getCustomer().getFirstName() +" "+ creditApplication.getCustomer().getLastName();
    }
}
