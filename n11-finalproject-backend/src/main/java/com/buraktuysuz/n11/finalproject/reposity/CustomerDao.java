package com.buraktuysuz.n11.finalproject.reposity;


import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Long> {

    Customer findByIdentityNumberAndBirthDate(String identityNumber, LocalDate birthday);
    Customer findByIdentityNumber(String identityNumber);

    @Modifying
    @Query("delete from CreditApplication creditApp where creditApp.customer.id = ?1")
    void deleteAllApplicationByCustomerId(Long customerId);
}
