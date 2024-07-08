import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../services/auth-service/auth.service";
import { TokenService } from "../services/token-service/token.service";
import { map, pipe } from "rxjs";

export const AuthGuard : CanActivateFn = () => {
    const router = inject(Router);
    const authService = inject(AuthService);

    if (authService.IsAuth()) {
      return true;
    }
  
    router.navigate(['/login']);
    return false;
};

export const TrySilentLogin : CanActivateFn = () => {
    const tokenService = inject(TokenService);

    if(tokenService.getToken() == null) return true;

    //If there is token in local storage try silentLogin
    const router = inject(Router);
    const authService = inject(AuthService);

    return authService.loginWithToken()
    .pipe(
        map(
            data => {
                if(data != null){
                    console.log("Silent login was successful")
                    router.navigate(['/home']);
                    return false;
                }
                else{
                    return true;
                }
            }
        )
    );
        
};

