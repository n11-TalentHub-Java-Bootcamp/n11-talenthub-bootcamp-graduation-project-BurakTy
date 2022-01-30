package com.buraktuysuz.n11.finalproject.entity.concretes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String firstName;

    @Column(nullable = false,length = 150)
    private String lastName;

    @Column(nullable = false,length = 11)
    private String identityNumber;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal currentSalary;

    @Column(nullable = false,length = 3)
    private String countryCode;

    @Column(nullable = false,length = 10)
    private String currentPhoneNumber;

    private String currentEmail;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private boolean isActive;

    @Column(length = 4)
    private int creditScore;

}
