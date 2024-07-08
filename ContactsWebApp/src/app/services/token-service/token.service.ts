import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  tokenStorageKey = 'access_token';
  token : string;

  constructor() { }

  setToken(token : string){
    console.log("TOKEN " + token)
    localStorage.setItem(this.tokenStorageKey,token)
  }

  getToken() : string | null{
    return localStorage.getItem('access_token');
  }

  invalidateToken(){
    localStorage.removeItem(this.tokenStorageKey);
  }
}
