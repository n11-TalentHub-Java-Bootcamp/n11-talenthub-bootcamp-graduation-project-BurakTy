import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpContext,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(
    private http: HttpClient,
  ) { }

  headers = new HttpHeaders().set( 'Content-Type',  'application/json; charset=utf-8');

  findByIdentityNumber(identityNumber: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/api/v1/customers/${identityNumber}`);
  }

  saveNewCustomer(customerRequestForm): Observable<any>{
    return this.http.post<any>(`${environment.apiUrl}/api/v1/customers`,customerRequestForm,{headers:this.headers});
  }

 updateNewCustomer(customerRequestForm): Observable<any>{
    return this.http.put<any>(`${environment.apiUrl}/api/v1/customers`,customerRequestForm,{headers:this.headers});
  }

  deleteNewCustomer(custumerId): Observable<any>{
    return this.http.delete<any>(`${environment.apiUrl}/api/v1/customers/${custumerId}`);
  }
}
