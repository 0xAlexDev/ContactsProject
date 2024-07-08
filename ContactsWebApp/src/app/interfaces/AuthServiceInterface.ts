import { Observable } from "rxjs";
import { LoginRequest } from "../models/LoginRequest";
import { RegistrationRequest } from "../models/RegistrationRequest";

export interface AuthServiceInterface {
    authPath : String;
    signInPath : String;
    signUpPath : String;
    loginWithCredentials( loginRequest : LoginRequest) : Observable<any>;
    registration( registrationRequest : RegistrationRequest) : Observable<any>;    
} 