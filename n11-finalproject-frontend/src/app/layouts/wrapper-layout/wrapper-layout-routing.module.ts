import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreditApplicationComponent } from 'src/app/views/credit-application/credit-application.component';
import { CustomerComponent } from 'src/app/views/customer/customer.component';
import { ResultCreditApplicationComponent } from 'src/app/views/result-credit-application/result-credit-application.component';
import { WrapperLayoutComponent } from './wrapper-layout.component';

const routes: Routes = [
  {
    path: '',
    component: WrapperLayoutComponent,
    children: [
  
      {
        path: 'customer',
        component: CustomerComponent,
      },
      {
        path: 'apply-credit-application',
        component: CreditApplicationComponent,
      },    
      {
        path: 'result-credit-application',
        component: ResultCreditApplicationComponent,
      },    
      {
        path: '',
        component: CreditApplicationComponent,
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'apply-credit-application',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WrapperLayoutRoutingModule { }
