import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class CreditAppplicationService {

  constructor(
    private http: HttpClient,
  ) { }

  
  headers = new HttpHeaders().set( 'Content-Type',  'application/json; charset=utf-8');

  getLastCreditApplicationResultByIdentityNumberAndBirthDay(identityNumber: string,birthDate:string): Observable<any> {
    let params=new HttpParams()
    .set('identityNumber',identityNumber)
    .set('birthday',birthDate)
    return this.http.get<any>(`${environment.apiUrl}/api/v1/creditapplication/result`,{params:params});
  }

  applyCreditApplication(creditApplicationForm:any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/v1/creditapplication`,creditApplicationForm,{headers:this.headers});
  }

}
