import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { TokenService } from '../services/token-service/token.service';
import { Router } from '@angular/router';
import { ValidationException } from '../config/CustomException/ValidationException';
import { NotFoundException } from '../config/CustomException/NotFoundException';

@Injectable({
  providedIn: 'root'
})
export class JwtInterceptorService implements HttpInterceptor {

  constructor(private tokenService : TokenService, private router : Router) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> 
  {
    let token = this.tokenService.getToken()
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMsg = '';
        switch (error.status) {
          case 403:
            throw Error("Bad request error");
          break;

          case 404:
            throw new NotFoundException(error.error,404);
          break;

          case 460:
            let validationErrors : string[] = error.error.errors;
            throw new ValidationException(error.message,validationErrors,error.status);
          break;

          case 490:
            this.tokenService.invalidateToken();
            this.router.navigate(['login']);
            throw Error("invalid token");
          break;

          case 491:
            throw Error("invalid roles");
          break;

          case 500:
            throw Error("internal server error");
          break;

          default:
            throw Error("Generic error");
          break;
        }
      })
    )
  }
}
