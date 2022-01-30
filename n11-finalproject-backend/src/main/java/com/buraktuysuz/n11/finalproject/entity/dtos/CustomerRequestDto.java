package com.buraktuysuz.n11.finalproject.entity.dtos;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerRequestDto {

    @NotEmpty(message = "Adı boş bırakılamaz")
    @Size(max = 150,message = "Maksimum 150 karakter olmalı")
    private String firstName;

    @NotEmpty(message = "Soyadı boş bırakılamaz")
    @Size(max = 150,message = "Maksimum 150 karakter olmalı")
    private String lastName;

    @Size(min = 11,max = 11,message = "11 haneli Tc kimlik numarası girilmelidir")
    private String identityNumber;

    @NotEmpty(message = "Maaş kısmı boş bırakılamaz")
    private BigDecimal currentSalary;

    @Size(min = 10,max = 10,message = "10 haneli telefon numarası girilmelidir")
    private String currentPhoneNumber;

    @Email
    private String currentEmail;

    private String countryCode;

    @Past()
    private LocalDate birthDate;


}
