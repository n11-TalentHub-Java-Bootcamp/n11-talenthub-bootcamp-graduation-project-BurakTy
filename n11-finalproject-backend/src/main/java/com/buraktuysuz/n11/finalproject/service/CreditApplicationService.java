package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.converter.CreditApplicationMapper;
import com.buraktuysuz.n11.finalproject.converter.CustomerMapper;
import com.buraktuysuz.n11.finalproject.entity.concretes.CreditApplication;
import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationFormDto;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationResultDto;
import com.buraktuysuz.n11.finalproject.exception.CustomerNotMatchException;
import com.buraktuysuz.n11.finalproject.reposity.CreditApplicationDao;

import com.buraktuysuz.n11.finalproject.utility.enums.EnumApplicationResult;
import com.buraktuysuz.n11.finalproject.utility.enums.EnumMessageType;
import com.buraktuysuz.n11.finalproject.utility.results.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CreditApplicationService {

    private CreditApplicationDao creditApplicationDao;
    private CustomerService customerService;
    private CalculateService calculateService;
    private MessageService messageService;

    @Transactional
    public DataResult<CreditApplicationResultDto> applyCreditApplication(CreditApplicationFormDto formDto) {
        Customer formCustomer= CustomerMapper.INSTANCE.convertCreditApplicationFormDtoToCustomer(formDto);
        var customer = customerService.findByIdentityNumber(formCustomer.getIdentityNumber());

        if(customer==null){
            formCustomer=customerService.save(formCustomer);
        }else{
            formCustomer.setId(customer.getId());
            formCustomer.setCreditScore(customer.getCreditScore());
        }

        BigDecimal deposit=formDto.getDeposit()==null?BigDecimal.ZERO:formDto.getDeposit();
        var creditApplication = createCreditApplication(formCustomer, deposit);
        var resultDto = CreditApplicationMapper.INSTANCE.convertToCreditApplicationResultDto(creditApplication);

        return new SuccessDataResult<>(resultDto);
    }



    // Last credit application result find by identity number and birthday
    public DataResult<CreditApplicationResultDto> findLastResultByCustomerIdentityNumberAndBirthDay(String identityNumber, LocalDate birthDay) {
        var customer = customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthDay);
        if (customer == null) {
            throw new CustomerNotMatchException("Identity number and birthday did not match");
        }

        var result=creditApplicationDao.findTopByCustomer_IdOrderByIdDesc(customer.getId());
        if(result==null){
            return new ErrorDataResult<>("No credit application found for this customer"+identityNumber);
        }

        CreditApplicationResultDto resultDto = CreditApplicationMapper.INSTANCE.convertToCreditApplicationResultDto(result);
        return new SuccessDataResult<>(resultDto);
    }

    // Lists all credit application results find by identity number and birthday
    public DataResult<List<CreditApplicationResultDto>> findAllResultByCustomerIdentityNumberAndBirthDay(String identityNumber, LocalDate birthday) {
        var customer = customerService.findCustomerByIdentityNumberAndBirthDay(identityNumber, birthday);
        if (customer == null) {
            throw new CustomerNotMatchException("Identity number and birthday did not match");
        }

        var result= creditApplicationDao.findAllByCustomer_IdOrderByIdDesc(customer.getId());
        if(result.isEmpty()){
            return new ErrorDataResult<>("No credit application found for this customer"+identityNumber);
        }

        List<CreditApplicationResultDto> resultDto = CreditApplicationMapper.INSTANCE.convertToCreditApplicationResultListDto(result);
        return new SuccessDataResult<>(resultDto);
    }

    // Determines the credit application result according to customer information and deposit
    private CreditApplication createCreditApplication(Customer customer, BigDecimal deposit) {
        CreditApplication newApp = new CreditApplication();
        newApp.setCustomer(customer);
        newApp.setApplicationTime(new Date());
        newApp.setDeposit(deposit);
        newApp.setSalary(customer.getCurrentSalary());

        if(existApprovedCreditApplicationCheckByCustomerId(customer.getId())){
            newApp=hasPreviousApprovedCredit(newApp);
        }else{
            var creditLimit = calculateService.creditLimitCalculate(customer, deposit);
            if (creditLimit.compareTo(BigDecimal.ZERO) == 0) {
                newApp.setEnumApplicationResult(EnumApplicationResult.NOTAPPROVED);
                newApp.setDescription("Credit score is not enough");
            } else {
                newApp.setEnumApplicationResult(EnumApplicationResult.APPROVED);
            }

            newApp.setCreditLimit(creditLimit);
            newApp = creditApplicationDao.save(newApp);
            messageService.sendMessage(newApp, EnumMessageType.OFFICE365);
        }

        return newApp;
    }


    // Checks a previously approved loan application by customer id
    private boolean existApprovedCreditApplicationCheckByCustomerId(Long customerId){
        var creditApplication=creditApplicationDao.findTopByCustomer_IdAndAndEnumApplicationResultOrderByIdDesc(customerId,EnumApplicationResult.APPROVED);
        return  creditApplication==null?false:true;
    }

    //  considered not approved because it is a previously approved loan application
    private CreditApplication hasPreviousApprovedCredit(CreditApplication newApp){
        newApp.setCreditLimit(BigDecimal.ZERO);
        newApp.setEnumApplicationResult(EnumApplicationResult.NOTAPPROVED);
        newApp.setDescription("Previous approved credit available");
        newApp = creditApplicationDao.save(newApp);
        messageService.sendMessage(newApp, EnumMessageType.OFFICE365);
        return newApp;
    }
}
