package com.buraktuysuz.n11.finalproject.reposity;


import com.buraktuysuz.n11.finalproject.entity.concretes.CreditApplication;

import com.buraktuysuz.n11.finalproject.utility.enums.EnumApplicationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CreditApplicationDao extends JpaRepository<CreditApplication,Long> {

    CreditApplication findTopByCustomer_IdAndAndEnumApplicationResultOrderByIdDesc(Long customerId, EnumApplicationResult result);
    CreditApplication findTopByCustomer_IdOrderByIdDesc(Long customerId);
    List<CreditApplication> findAllByCustomer_IdOrderByIdDesc(Long customerId);



}
