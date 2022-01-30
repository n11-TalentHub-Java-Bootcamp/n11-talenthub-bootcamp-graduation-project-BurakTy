package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.entity.concretes.CreditApplication;
import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationFormDto;
import com.buraktuysuz.n11.finalproject.exception.CustomerNotMatchException;
import com.buraktuysuz.n11.finalproject.reposity.CreditApplicationDao;
import com.buraktuysuz.n11.finalproject.utility.enums.EnumApplicationResult;
import com.buraktuysuz.n11.finalproject.utility.results.ErrorDataResult;
import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditApplicationServiceTest extends TestCase {

    @InjectMocks
    private CreditApplicationService creditApplicationService;

    @Mock
    private CreditApplicationDao creditApplicationDao;

    @Mock
    private CustomerService customerService;

    @Mock
    private CalculateService calculateService;

    @Mock
    private MessageService messageService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void shouldNotApprovedCreditApplicationWhenCreditScorelessThen500() {
        CreditApplicationFormDto creditApplicationFormDto= TestDataProvider.getFullValueCreditApplicationFormDto();
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        testCustomer.setCreditScore(490);
        when(customerService.findByIdentityNumber(testCustomer.getIdentityNumber())).thenReturn(null);
        when(customerService.save(any())).thenReturn(testCustomer);
        when(calculateService.creditLimitCalculate(testCustomer,BigDecimal.ZERO)).thenReturn(new BigDecimal(0));
        var testCreditApplication=new CreditApplication();
        testCreditApplication.setCustomer(testCustomer);
        testCreditApplication.setCreditLimit(new BigDecimal(0));
        testCreditApplication.setEnumApplicationResult(EnumApplicationResult.NOTAPPROVED);
        when(creditApplicationDao.save(any())).thenReturn(testCreditApplication);
        var result= creditApplicationService.applyCreditApplication(creditApplicationFormDto);
        assertTrue(result.isSuccess());
        assertEquals(result.getData().getEnumApplicationResult(),EnumApplicationResult.NOTAPPROVED);
    }

    @Test
    public void shouldApprovedCreditApplicationWhenCreditScoreGreaterThen500() {
        CreditApplicationFormDto creditApplicationFormDto= TestDataProvider.getFullValueCreditApplicationFormDto();
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        testCustomer.setCreditScore(510);
        when(customerService.findByIdentityNumber(testCustomer.getIdentityNumber())).thenReturn(null);
        when(customerService.save(any())).thenReturn(testCustomer);
        when(calculateService.creditLimitCalculate(testCustomer,BigDecimal.ZERO)).thenReturn(new BigDecimal(10000));
        var testCreditApplication=new CreditApplication();
        testCreditApplication.setCustomer(testCustomer);
        testCreditApplication.setCreditLimit(new BigDecimal(10000));
        testCreditApplication.setEnumApplicationResult(EnumApplicationResult.APPROVED);
        when(creditApplicationDao.save(any())).thenReturn(testCreditApplication);
        var result= creditApplicationService.applyCreditApplication(creditApplicationFormDto);
        assertTrue(result.isSuccess());
        assertEquals(result.getData().getEnumApplicationResult(),EnumApplicationResult.APPROVED);
    }

    @Test
    public void shouldNotReturnResultListWhenCustomerNotMatch() {
        String identityNumber = "11111111111";
        LocalDate birthday = LocalDate.now();
        Customer customer = Customer.builder().id(1L).build();
        when(customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday)).thenReturn(null);
        expectedException.expect(CustomerNotMatchException.class);
        expectedException.expectMessage("Identity number and birthday did not match");
        var result=creditApplicationService.findAllResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldReturnSuccessResultListWhenCustomerMatch() {
        String identityNumber = "11111111111";
        LocalDate birthday = LocalDate.now();
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        var testCreditApplication=new CreditApplication();
        testCreditApplication.setCustomer(testCustomer);
        testCreditApplication.setCreditLimit(BigDecimal.ZERO);
        testCreditApplication.setEnumApplicationResult(EnumApplicationResult.APPROVED);
        List<CreditApplication> testList =  Arrays.asList(testCreditApplication,testCreditApplication);

        when(customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday)).thenReturn(testCustomer);
        when(creditApplicationDao.findAllByCustomer_IdOrderByIdDesc(testCustomer.getId())).thenReturn(testList);
        var result=creditApplicationService.findAllResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldReturnErrorResultListWhenCustomerMatch() {
        String identityNumber = "11111111111";
        LocalDate birthday = LocalDate.parse("1995-01-01");
        Customer testCustomer=TestDataProvider.getFullValueCustomer();
        List<CreditApplication> emtyList=new ArrayList<>();
        when(customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday)).thenReturn(testCustomer);
        when(creditApplicationDao.findAllByCustomer_IdOrderByIdDesc(testCustomer.getId())).thenReturn(emtyList);
        var result=creditApplicationService.findAllResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        assertFalse(result.isSuccess());
    }

    @Test
    public void shouldNotReturnLastResultWhenCustomerNotMatch() {
        String identityNumber = "11111111111";
        LocalDate birthday = LocalDate.parse("1995-01-01");
        Customer customer = Customer.builder().id(1L).build();
        when(customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday)).thenReturn(null);
        expectedException.expect(CustomerNotMatchException.class);
        expectedException.expectMessage("Identity number and birthday did not match");
        var result=creditApplicationService.findLastResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        assertTrue(result.isSuccess());

    }

    @Test
    public void shouldReturnSuccessLastResultWhenCustomerMach() {
        String identityNumber = "11111111111";
        LocalDate birthday = LocalDate.now();
        Customer testCustomer=TestDataProvider.getFullValueCustomer();

        var testCreditApplication=new CreditApplication();
        testCreditApplication.setCustomer(testCustomer);
        testCreditApplication.setCreditLimit(BigDecimal.ZERO);
        testCreditApplication.setEnumApplicationResult(EnumApplicationResult.APPROVED);
        when(customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday)).thenReturn(testCustomer);
        when(creditApplicationDao.findTopByCustomer_IdOrderByIdDesc(testCustomer.getId())).thenReturn(testCreditApplication);
        var result=creditApplicationService.findLastResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldReturnErrorLastResultWhenCustomerMach() {
        String identityNumber = "11111111111";
        LocalDate birthday = LocalDate.now();
        var testCustomer=Customer.builder()
                .id(1L)
                .birthDate(LocalDate.parse("1995-01-01"))
                .identityNumber("111111111111")
                .build();

        when(customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday)).thenReturn(testCustomer);
        when(creditApplicationDao.findTopByCustomer_IdOrderByIdDesc(testCustomer.getId())).thenReturn(null);
        var result=creditApplicationService.findLastResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        assertNotSame(new ErrorDataResult<>("No credit application found for this customer"+identityNumber),result);
    }

    @Test
    public void existApprovedCreditApplicationCheckByCustomerId() {
        CreditApplication creditApplication = CreditApplication.builder().id(1L).enumApplicationResult(EnumApplicationResult.APPROVED).build();
        when(creditApplicationDao.findTopByCustomer_IdAndAndEnumApplicationResultOrderByIdDesc(1L, EnumApplicationResult.APPROVED)).thenReturn(creditApplication);
        var result =creditApplicationDao.findTopByCustomer_IdAndAndEnumApplicationResultOrderByIdDesc(1L, EnumApplicationResult.APPROVED);
        assertNotNull(result);
    }

    @Test
    public void nonExistApprovedCreditApplicationCheckByCustomerId() {
        when(creditApplicationDao.findTopByCustomer_IdAndAndEnumApplicationResultOrderByIdDesc(1L, EnumApplicationResult.APPROVED)).thenReturn(null);
        var result =creditApplicationDao.findTopByCustomer_IdAndAndEnumApplicationResultOrderByIdDesc(1L, EnumApplicationResult.APPROVED);
        assertNull(result);

    }


}