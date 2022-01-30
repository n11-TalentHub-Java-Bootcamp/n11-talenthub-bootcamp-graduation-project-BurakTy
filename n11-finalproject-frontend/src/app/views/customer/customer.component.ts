import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { reduce } from 'rxjs';
import { CustomerService } from 'src/app/service/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  customerForm: FormGroup;
  identityNumber: string = "";
  showForm = false;
  foundCustomer;
  foundCustomerMessage;
  customerResult;
  loader = false;
  constructor(
    private formBuilder: FormBuilder,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.createCustomerForm();
  }

  openDatePicker(d) {
    d.toggle()
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
    const date = new Date(`${datepickerValue.year}-${datepickerValue.month}-${datepickerValue.day}`);
    return date
  }  
  resetMessage() {
    this.foundCustomerMessage = "";
  }
  resetForm() {
    this.customerForm.reset();
    this.identityNumber="";
    this.resetMessage();
  }
  createCustomerForm() {
    this.customerForm = this.formBuilder.group({
      id: [""],
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      identityNumber: ["", Validators.required],
      currentSalary: [0, Validators.required],
      currentPhoneNumber: ["", Validators.required],
      currentEmail: ["", Validators.email],
      countryCode: [""],
      birthDate: ["", Validators.required],
    })
  }

  findCustumerByIdentityNumber() {
    this.showForm = false;
    this.customerResult={};
    if (this.identityNumber.length == 11) {
      this.customerService.findByIdentityNumber(this.identityNumber).subscribe(res => {
        if (res.data != null) {
          this.customerForm.setValue({
            id:res.data.id,
            firstName: res.data.firstName,
            lastName: res.data.lastName,
            identityNumber: res.data.identityNumber,
            currentSalary: res.data.currentSalary,
            currentPhoneNumber: res.data.currentPhoneNumber,
            currentEmail: res.data.currentEmail,
            countryCode: "90",
            birthDate: this.convertBirthDateFormatToDatePicker(res.data.birthDate),
          });
          this.foundCustomer = true;
        } else {
          this.customerForm.reset();
          this.customerForm.controls["identityNumber"].setValue(this.identityNumber);
          this.customerForm.controls["countryCode"].setValue('90');
          this.foundCustomer = false;
          this.foundCustomerMessage = `${this.identityNumber} Tc kimlik numarasına ait müşteri bulunmadı. Formu doldurarak yeni müşteri kaydı oluşturabilirsiniz`
        }
        this.showForm = true;
      })
    }
  }

  saveCustomer(){
    this.loader=true;
    this.customerResult={};
    let customerRequestForm=Object.assign({},this.customerForm.value);
    customerRequestForm.birthDate = this.convertDatePickerToDate(customerRequestForm.birthDate);
    delete customerRequestForm["id"];
    this.customerService.saveNewCustomer(customerRequestForm).subscribe(
      res=>{
        this.loader=false;
        this.customerResult=res.data;
      },err=>{
        this.loader=false;
        this.customerResult={};
        this.customerResult["success"]=false;
        console.error(err);
      }
    )

  }
  updateCustomer(){
    this.loader=true;
    this.customerResult={};
    let customerRequestForm=Object.assign({},this.customerForm.value);
    customerRequestForm.birthDate = this.convertDatePickerToDate(customerRequestForm.birthDate);
    this.customerService.updateNewCustomer(customerRequestForm).subscribe(
      res=>{
        this.loader=false;
        this.customerResult=res;
      },err=>{
        this.loader=false;
        this.customerResult={};
        this.customerResult["success"]=false;
        console.error(err);
      }
    )

  }

  deleteCustomer(){
    this.loader=true;
    this.customerResult={};
    let customerRequestForm=Object.assign({},this.customerForm.value);
    customerRequestForm.birthDate = this.convertDatePickerToDate(customerRequestForm.birthDate);
    this.customerService.deleteNewCustomer(customerRequestForm.id).subscribe(
      res=>{
        this.loader=false;
        this.customerResult=res;
        this.resetForm();
      },err=>{
        this.loader=false;
        this.customerResult={};
        this.customerResult["success"]=false;
        console.error(err);
      }
    )

  }


 

}
