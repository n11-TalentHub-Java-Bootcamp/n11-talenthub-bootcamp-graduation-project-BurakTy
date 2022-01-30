import { Component, OnInit } from '@angular/core';
import { CreditAppplicationService } from 'src/app/service/credit-appplication.service';

@Component({
  selector: 'app-result-credit-application',
  templateUrl: './result-credit-application.component.html',
  styleUrls: ['./result-credit-application.component.css']
})
export class ResultCreditApplicationComponent implements OnInit {

  identityNumber: string = "";
  datepickerValue={day:"",year:"",month:""};
  foundCustomerMessage;
  foundCustomer;
  applicationresult;
  constructor(private creditApplicationService: CreditAppplicationService,) { }

  ngOnInit(): void {
  }
  convertDatePickerToDate(datepickerValue) {
    const date = new Date(`${datepickerValue.year}-${datepickerValue.month}-${datepickerValue.day+1}`);
    return date
  }
  resetMessage() {
    this.foundCustomerMessage = "";
  }
  openDatePicker(d) {
    if (!this.foundCustomer) {
      d.toggle()
    }
  }
  getCreditApplicationResult() {
    this.foundCustomerMessage="";
    let birthDate =this.convertDatePickerToDate(this.datepickerValue)
    this.creditApplicationService.getLastCreditApplicationResultByIdentityNumberAndBirthDay(this.identityNumber, birthDate.toISOString().substring(0,10)).subscribe(
      res=>{
        this.applicationresult=res.data;
      },err=>{
        this.foundCustomerMessage="Müşteri Bilgisi Bulunamadı";
      }
    )
    
  }


}
