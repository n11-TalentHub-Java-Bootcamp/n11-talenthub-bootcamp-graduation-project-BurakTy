package com.buraktuysuz.n11.finalproject.controller;


import com.buraktuysuz.n11.finalproject.converter.CustomerMapper;
import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationResultDto;
import com.buraktuysuz.n11.finalproject.entity.dtos.CustomerRequestDto;
import com.buraktuysuz.n11.finalproject.service.CreditApplicationService;
import com.buraktuysuz.n11.finalproject.service.CustomerService;
import com.buraktuysuz.n11.finalproject.utility.results.*;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
@CrossOrigin
public class CustomerController {

    private CustomerService customerService;
    private CreditApplicationService creditApplicationService;

    @GetMapping("/{identityNumber}")
    public ResponseEntity<DataResult<Customer>> findByIdentityNumber(@PathVariable String identityNumber) {
        var customer=customerService.findByIdentityNumber(identityNumber);
        return ResponseEntity.ok(new SuccessDataResult<>(customer));
    }

    @GetMapping("/allresult")
    public ResponseEntity<DataResult<List<CreditApplicationResultDto>>> getAllCreditApplicationResultByIdentityNumberAndBirthDay(@RequestParam String identityNumber, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate birthday) {
        var result=creditApplicationService.findAllResultByCustomerIdentityNumberAndBirthDay(identityNumber,birthday);
        if(!result.isSuccess()){
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("")
    public ResponseEntity<DataResult<Customer>> addNewCustomer(@RequestBody CustomerRequestDto requestDto) {
        var customer = CustomerMapper.INSTANCE.convertCustomerRequestDtoToCustomer(requestDto);
        customer= customerService.save(customer);
        return new ResponseEntity(new SuccessDataResult<Customer>(customer,"New customer successfully created"), HttpStatus.CREATED);    }

    @PutMapping("")
    public ResponseEntity<DataResult<Customer>> updateCustomerInformation(@RequestBody Customer customer) {
        customer= customerService.update(customer);
        return ResponseEntity.ok(new SuccessDataResult<Customer>(customer,"customer successfully updated"));
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Result> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.ok(new SuccessResult("This " +customerId + " customer has been successfully deleted"));
    }

}
