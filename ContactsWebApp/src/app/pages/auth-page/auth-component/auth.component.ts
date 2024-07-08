import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms'
import { LottieComponent } from 'ngx-lottie';
import { Button } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AuthService } from '../../../services/auth-service/auth.service';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../../models/LoginRequest';
import { RegistrationRequest } from '../../../models/RegistrationRequest';
import { catchError, of, tap, throwError } from 'rxjs';
import { ValidationException } from '../../../config/CustomException/ValidationException';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { NotFoundException } from '../../../config/CustomException/NotFoundException';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    Button,
    LottieComponent,
    CommonModule, 
    ReactiveFormsModule,
    InputTextModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit {

  loginForm: FormGroup;
  registrationForm: FormGroup;

  authPashe : AuthPhases = AuthPhases.LOGIN;

  allAuthPhases = AuthPhases;

  constructor(private authService : AuthService, private userService : UserService, private router : Router,private toastMessageService: MessageService){}

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl(''),
    });

    this.registrationForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl(''),
    });
  }

  onLoginSubmit(form : FormGroup){
    let loginRequest : LoginRequest = new LoginRequest(form.controls["username"].value,form.controls["password"].value);

    this.authService.loginWithCredentials(loginRequest).subscribe(
      {
        next: (data) => {
          this.router.navigate(['/home']);
        },
        error: (err : any) => { 
          if(err instanceof ValidationException){
            err.validationErrors.forEach(validationError => this.showErrorToast(validationError))
          }
          if(err instanceof NotFoundException){
            this.showErrorToast("User not found, check Username and Password")
          }
        }
      }
    );

  }
  onRegistrationSubmit(form : FormGroup){

    let username : string = form.controls["username"].value;
    let password : string = form.controls["password"].value;

    let registrationRequest : RegistrationRequest = new RegistrationRequest(username,password);

    this.authService.registration(registrationRequest).subscribe(
      {
        next: (data) => {
          this.loginForm.controls["username"].setValue(username);
          this.loginForm.controls["password"].setValue(password);
          this.authPashe = AuthPhases.LOGIN;
        },
        error: (err : any) => { 
          if(err instanceof ValidationException){
            err.validationErrors.forEach(validationError => this.showErrorToast(validationError))
          }
        }
      }
    );


    
  }

  private showSuccessToast(message : string) {
    this.toastMessageService.add({ severity: 'success', summary: 'Success', detail: message });
  }

  private showErrorToast(message : string) {
    this.toastMessageService.add({ severity: 'error', summary: 'Error', detail: message });
  }

  changeAuthPhase(){
    this.authPashe = this.authPashe == AuthPhases.LOGIN ? this.authPashe = AuthPhases.REGISTRATION : AuthPhases.LOGIN
  }

}

enum AuthPhases {
  LOGIN,
  REGISTRATION
}
