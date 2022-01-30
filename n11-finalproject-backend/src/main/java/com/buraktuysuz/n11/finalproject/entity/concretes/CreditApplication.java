package com.buraktuysuz.n11.finalproject.entity.concretes;

import com.buraktuysuz.n11.finalproject.utility.enums.EnumApplicationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "credit_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationTime;

    private EnumApplicationResult enumApplicationResult;

    @Column(nullable = false,precision = 19,scale = 0)
    private BigDecimal creditLimit;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal deposit;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", foreignKey = @ForeignKey(name = "fk_credit_application_customer_id"), nullable = false)
    private Customer customer;

    private String description;
}
