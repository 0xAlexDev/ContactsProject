import { Injectable } from '@angular/core';
import { CrudService } from '../crud-service/crud.service';
import { AuthServiceInterface } from '../../interfaces/AuthServiceInterface';
import { LoginRequestDTO } from '../../models/DTOs/LoginRequestDTO';
import { RegistrationRequestDTO } from '../../models/DTOs/RegistrationRequestDTO';
import { map, Observable } from 'rxjs';
import { AppConfiguration } from '../../config/AppConfiguration';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenService } from '../token-service/token.service';
import { UserService } from '../user-service/user.service';
import { User } from '../../models/User';
import { AuthResponseDTO } from '../../models/DTOs/AuthResponseDTO';
import { LoginRequest } from '../../models/LoginRequest';
import { RegistrationRequest } from '../../models/RegistrationRequest';
import { AuthResponse } from '../../models/AuthResponse';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements AuthServiceInterface {

  constructor(private http : HttpClient, private tokenService : TokenService, private router : Router) { }

  authPath : string = AppConfiguration.baseApiPath + "auth"

  signUpPath : String = "/signup";

  signInPath : String = "/signinWithCredentials";

  signInWithToken : String = "/signinWithToken";

  authUser : User | null;

  headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

  loginWithCredentials(loginRequest: LoginRequest) : Observable<AuthResponse> {

    let loginRequestDTO : LoginRequestDTO = this.loginRequestToDTO(loginRequest)

    return this.http.post<AuthResponseDTO>(this.authPath+this.signInPath,loginRequestDTO,{headers:this.headers}).pipe(map(response => {
      return this.handleLoginResponse(response);
   }))

  }

  loginWithToken() : Observable<AuthResponse> {

    return this.http.post<AuthResponseDTO>(this.authPath+this.signInWithToken,{headers:this.headers}).pipe(map(response => {
      return this.handleLoginResponse(response);
   }))

  }
  

  private handleLoginResponse(response: AuthResponseDTO) {
    let token: string = response.token;
    this.tokenService.setToken(token);

    //set current auth user
    this.authUser = User.factoryFromDTO(response.user);

    //return entity
    let authResponse: AuthResponse = AuthResponse.factoryFromDTO(response);

    return authResponse;
  }

  /** 
   * @throws {Error}
   */
  registration(registrationRequest: RegistrationRequest) : Observable<Boolean> {

    let registrationRequestDTO : RegistrationRequestDTO = this.registrationRequestToDTO(registrationRequest);
    return this.http.post<Boolean>(this.authPath+this.signUpPath,registrationRequestDTO,{headers:this.headers});
  }

  IsAuth() : Boolean{
    if(this.tokenService.getToken() != null && this.authUser != null) return true;

    return false;
  }

  logout() : Boolean{
    this.tokenService.invalidateToken();
    this.authUser = null;
    this.router.navigate(['/login']);
    return false;
  }

  private loginRequestToDTO(loginRequest: LoginRequest): LoginRequestDTO {
    return {username : loginRequest.username, password : loginRequest.password}
  }

  private registrationRequestToDTO(registrationRequest: RegistrationRequest) : RegistrationRequestDTO{
    return {username : registrationRequest.username, password : registrationRequest.password}
  }

 
}
