package com.buraktuysuz.n11.finalproject.controller;


import com.buraktuysuz.n11.finalproject.converter.CreditApplicationMapper;
import com.buraktuysuz.n11.finalproject.converter.CustomerMapper;
import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationFormDto;
import com.buraktuysuz.n11.finalproject.entity.dtos.CreditApplicationResultDto;
import com.buraktuysuz.n11.finalproject.service.CreditApplicationService;
import com.buraktuysuz.n11.finalproject.service.CustomerService;
import com.buraktuysuz.n11.finalproject.utility.results.DataResult;
import com.buraktuysuz.n11.finalproject.utility.results.ErrorDataResult;
import com.buraktuysuz.n11.finalproject.utility.results.SuccessDataResult;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/creditapplication")
@AllArgsConstructor
@CrossOrigin
public class CreditApplicationController {

    private CreditApplicationService creditApplicationService;

    @GetMapping("/result")
    public ResponseEntity<DataResult<CreditApplicationResultDto>> getLastCreditApplicationResultByIdentityNumberAndBirthDay(@RequestParam String identityNumber, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam String birthday) {
        LocalDate birthdate=LocalDate.parse(birthday);
        var result = creditApplicationService.findLastResultByCustomerIdentityNumberAndBirthDay(identityNumber, birthdate);
        if (!result.isSuccess()) {
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<DataResult<CreditApplicationResultDto>> applyCreditApplication(@RequestBody CreditApplicationFormDto formDto) {
        var result = creditApplicationService.applyCreditApplication(formDto);
        return ResponseEntity.ok(result);
    }
}
