import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { CreditAppplicationService } from 'src/app/service/credit-appplication.service';
import { CustomerService } from 'src/app/service/customer.service';

@Component({
  selector: 'app-credit-application',
  templateUrl: './credit-application.component.html',
  styleUrls: ['./credit-application.component.css']
})
export class CreditApplicationComponent implements OnInit {

  creditApplicationForm: FormGroup;
  identityNumber: string = "";
  showForm = false;
  foundCustomer;
  foundCustomerMessage;
  haveDeposit = false;
  applicationresult;
  loader = false;
  constructor(
    private formBuilder: FormBuilder,
    private creditApplicationService: CreditAppplicationService,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.createCreditApplicationForm();
  }

  get creditApplicationControls() {
    return this.creditApplicationForm.controls;
  }
  openDatePicker(d) {
    if (!this.foundCustomer) {
      d.toggle()
    }
  }
  createCreditApplicationForm() {
    this.creditApplicationForm = this.formBuilder.group({
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      identityNumber: ["", Validators.required],
      currentSalary: [0, Validators.required],
      currentPhoneNumber: ["", Validators.required],
      currentEmail: ["", Validators.email],
      countryCode: [""],
      birthDate: ["", Validators.required],
      deposit: [0],
    })
  }

  applyCreditApplication() {
    this.loader = true;
    var postForm = Object.assign({}, this.creditApplicationForm.value);
    postForm.birthDate = this.convertDatePickerToDate(postForm.birthDate);
    this.creditApplicationService.applyCreditApplication(postForm).subscribe(
      res => {
        this.loader = false;
        this.applicationresult = res.data;
      },
      err=>{
        console.error(err);
        this.loader = false;
      }
    )
  }

  convertBirthDateFormatToDatePicker(d: Date) {
    const date = new Date(d);
    var returndate = {
      day: date.getDate(),
      month: date.getMonth() + 1,
      year: date.getFullYear()
    }
    return returndate
  }
  convertDatePickerToDate(datepickerValue) {
    const date = new Date(`${datepickerValue.year}-${datepickerValue.month}-${datepickerValue.day+1}`);
    return date
  }
  resetMessage() {
    this.foundCustomerMessage = "";
  }
  findCustumerByIdentityNumber() {
    this.showForm = false;
    if (this.identityNumber.length == 11) {
      this.customerService.findByIdentityNumber(this.identityNumber).subscribe(res => {
        if (res.data != null) {
          this.creditApplicationForm.setValue({
            firstName: res.data.firstName,
            lastName: res.data.lastName,
            identityNumber: res.data.identityNumber,
            currentSalary: res.data.currentSalary,
            currentPhoneNumber: res.data.currentPhoneNumber,
            currentEmail: res.data.currentEmail,
            countryCode: "90",
            birthDate: this.convertBirthDateFormatToDatePicker(res.data.birthDate),
            deposit: 0
          });
          this.foundCustomer = true;
        } else {
          this.creditApplicationForm.reset();
          this.creditApplicationControls["identityNumber"].setValue(this.identityNumber);
          this.creditApplicationControls["countryCode"].setValue('90');
          this.foundCustomer = false;
          this.foundCustomerMessage = `${this.identityNumber} Tc kimlik numarasına ait müşteri bulunmadı. Form Bilgilerini doldurup devam ediniz. Sistem yeni müşteri kaydı oluşturacaktır`
        }
        this.showForm = true;
      })
    }
  }

}
