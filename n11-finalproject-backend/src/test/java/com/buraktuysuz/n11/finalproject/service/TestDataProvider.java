package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationFormDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDataProvider {

    public static Customer getFullValueCustomer() {
        return Customer.builder()
                .id(1L)
                .birthDate(LocalDate.parse("1995-01-01"))
                .countryCode("90")
                .currentEmail("mail@mail.com")
                .currentSalary(new BigDecimal(5000))
                .currentPhoneNumber("5346786767")
                .firstName("first name")
                .lastName("last name")
                .identityNumber("111111111111")
                .creditScore(1200)
                .isActive(true)
                .build();
    }

    public static CreditApplicationFormDto getFullValueCreditApplicationFormDto() {
        CreditApplicationFormDto creditApplicationFormDto = new CreditApplicationFormDto();
        creditApplicationFormDto.setBirthDate(LocalDate.parse("1995-01-01"));
        creditApplicationFormDto.setCurrentSalary(new BigDecimal(5000));
        creditApplicationFormDto.setCurrentEmail("tuysuzburak@gmail.com");
        creditApplicationFormDto.setIdentityNumber("11111111111");
        creditApplicationFormDto.setCurrentPhoneNumber("11111111111");
        creditApplicationFormDto.setFirstName("First Name");
        creditApplicationFormDto.setLastName("Last Name");
        creditApplicationFormDto.setCountryCode("90");
        return creditApplicationFormDto;
    }

}
