package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.exception.AlreadyExistException;
import com.buraktuysuz.n11.finalproject.reposity.CustomerDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerDao customerDao;
    private CalculateService calculateService;

    public Customer findCustomerById(Long customerId){
        var customer =customerDao.findById(customerId);
        return customer.isEmpty()?null:customer.get();
    }
    public Customer save(Customer customer){
        var existCustomer= findByIdentityNumber(customer.getIdentityNumber());
        if(existCustomer!=null){
            throw new AlreadyExistException("This identityNumber already exitst");
        }
        int creditScore=calculateService.randomCreditScoreCalculate(customer);
        customer.setActive(true);
        customer.setCreditScore(creditScore);
        return customerDao.save(customer);
    }
    public Customer update(Customer customer){
        return customerDao.save(customer);
    }

    @Transactional
    public void deleteById(Long customerId){
        var customer=findCustomerById(customerId);
        if(customer==null){
            throw new NotFoundException( customerId+" id users not found");
        }
        customerDao.deleteAllApplicationByCustomerId(customerId);
        customerDao.delete(customer);
    }

    public Customer findCustomerByIdentityNumberAndBirthDay(String identityNumber, LocalDate birthDay){
        return customerDao.findByIdentityNumberAndBirthDate(identityNumber,birthDay);
    }
    public Customer findByIdentityNumber(String identityNumber){
        return customerDao.findByIdentityNumber(identityNumber);
    }



}
