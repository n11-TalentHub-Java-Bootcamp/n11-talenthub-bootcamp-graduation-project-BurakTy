import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
    if(localStorage.getItem("islogin")=="true"){
      this.login();
    }
  }

  login(){
    localStorage.setItem("islogin","true");
    this.router.navigate(["/main/creditapplication"]);
  }
}
