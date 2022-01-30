import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WrapperLayoutRoutingModule } from './wrapper-layout-routing.module';
import { CustomerComponent } from 'src/app/views/customer/customer.component';
import { CreditApplicationComponent } from 'src/app/views/credit-application/credit-application.component';
import { WrapperLayoutComponent } from './wrapper-layout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ResultCreditApplicationComponent } from 'src/app/views/result-credit-application/result-credit-application.component';



@NgModule({
  declarations: [
    WrapperLayoutComponent,
    CustomerComponent,
    CreditApplicationComponent,
    ResultCreditApplicationComponent
  ],
  imports: [
    CommonModule,
    WrapperLayoutRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ]
})
export class WrapperLayoutModule { }
