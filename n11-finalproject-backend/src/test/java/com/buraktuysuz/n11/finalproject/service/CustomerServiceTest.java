package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.exception.AlreadyExistException;
import com.buraktuysuz.n11.finalproject.reposity.CustomerDao;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private CalculateService calculateService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void findCustomerById() {
        Customer customer = Customer.builder()
                .id(1L)
                .birthDate(LocalDate.parse("1995-01-01"))
                .build();

        when(customerDao.findById(1L)).thenReturn(Optional.ofNullable(customer));
        var result = customerService.findCustomerById(1L);
        assertEquals(customer, result);
    }

    @Test
    public void shouldNotSaveWhenIdentityNumberAlreadyExist() {
        Customer customer = Customer.builder()
                .id(1L)
                .identityNumber("11111111111")
                .birthDate(LocalDate.parse("1995-01-01"))
                .build();

        when(customerService.findByIdentityNumber(customer.getIdentityNumber())).thenReturn(customer);
        expectedException.expect(AlreadyExistException.class);
        expectedException.expectMessage("This identityNumber already exitst");
        customerService.save(customer);

    }
    @Test
    public void shouldSaveWhenIdentityNumberAlreadyNotExist() {
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        when(customerService.findByIdentityNumber(testCustomer.getIdentityNumber())).thenReturn(null);
        when(calculateService.randomCreditScoreCalculate(testCustomer)).thenReturn(500);
        when(customerDao.save(testCustomer)).thenReturn(testCustomer);
        var result=customerService.save(testCustomer);
        assertEquals(testCustomer,result);
    }

    @Test
    public void update() {
        Customer customer = Customer.builder()
                .id(1L)
                .identityNumber("11111111111")
                .birthDate(LocalDate.parse("1995-01-01"))
                .build();
        customerService.update(customer);
    }

    @Test
    public void shouldNotDeleteWhenNotFoundCustomer() {
        Long customerId=1L;
        Customer customer=Customer.builder().id(customerId).build();
        when(customerDao.findById(customerId)).thenReturn(Optional.ofNullable(null));
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(customerId + " id users not found");
        customerService.deleteById(customerId);
    }

    @Test
    public void shouldDeleteWhenFoundCustomer() {
        Long customerId=1L;
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        when(customerDao.getById(customerId)).thenReturn(testCustomer);
        when(customerDao.findById(customerId)).thenReturn(Optional.ofNullable(testCustomer));
        customerService.deleteById(customerId);
    }


    @Test
    public void findCustomerByIdentityNumberAndBirthDay() {
        String identityNumber="111111111111";
        LocalDate birthDay=LocalDate.parse("1995-01-01");
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        when(customerDao.findByIdentityNumberAndBirthDate(identityNumber,birthDay)).thenReturn(testCustomer);
       var result=customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber,birthDay);
       assertEquals(result,testCustomer);
    }

    @Test
    public void findByIdentityNumber() {
        String identityNumber="111111111111";
        var testCustomer=Customer.builder().identityNumber(identityNumber).build();
        when(customerDao.findByIdentityNumber(identityNumber)).thenReturn(testCustomer);
        var result=customerService.findByIdentityNumber(identityNumber);
        assertEquals(result,testCustomer);
    }
}