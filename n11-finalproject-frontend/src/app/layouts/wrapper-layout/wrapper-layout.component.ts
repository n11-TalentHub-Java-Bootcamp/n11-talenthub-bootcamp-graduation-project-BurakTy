import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-wrapper-layout',
  templateUrl: './wrapper-layout.component.html',
  styleUrls: ['./wrapper-layout.component.css']
})
export class WrapperLayoutComponent implements OnInit {

  constructor(private router :Router) { }

  ngOnInit(): void {
  }


  logout(){
    localStorage.removeItem("islogin");
    this.router.navigate(["/"]);
  }
}
