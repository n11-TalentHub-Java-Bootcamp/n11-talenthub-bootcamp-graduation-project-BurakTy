package com.buraktuysuz.n11.finalproject.service;

import com.buraktuysuz.n11.finalproject.entity.concretes.Customer;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.function.DoubleBinaryOperator;

@Service
public class CalculateService {


    final int creditMultiplier = 4;

    public int randomCreditScoreCalculate(Customer customer) {
        int k=0;
        char numberList[] =customer.getIdentityNumber().toCharArray();
        for (var i:numberList) {
            k+= Character.getNumericValue(i);
        }
        if(k%4==0){
            return 1001;
        }else{
            return 100* Character.getNumericValue(numberList[0])+10*Character.getNumericValue(numberList[1])+Character.getNumericValue(numberList[2]);
        }
    }

    public BigDecimal creditLimitCalculate(Customer customer, BigDecimal deposit) {
        return resultByCreditScoreAndSalaryAndDeposit(customer.getCreditScore(), customer.getCurrentSalary(), deposit);
    }


    private BigDecimal resultByCreditScoreAndSalaryAndDeposit(int creditScore, BigDecimal salary, BigDecimal deposit) {
        double result = 0;
        if (creditScore >= 500 && creditScore < 1000) {
            result = limitBySalaryAndRateDeposit(salary.doubleValue(), deposit.doubleValue());
        } else if (creditScore >= 1000) {
            result = salary.doubleValue() * creditMultiplier + (deposit.doubleValue() * 0.5);
        }
        return new BigDecimal(result);
    }

    private double limitBySalaryAndRateDeposit(double salary, double deposit) {
        double limit = 0;

        if (salary < 5000) {
            limit = 10000 + deposit * 0.1;
        } else if (salary >= 5000 && salary < 10000) {
            limit = 20000 + deposit * 0.2;
        } else {
            limit = salary * (creditMultiplier / 2) + deposit * 0.25;
        }

        return limit;
    }

}
