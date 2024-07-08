import { Routes } from '@angular/router';
import { AuthGuard, TrySilentLogin } from './config/RouteGuards';

export const routes: Routes = [
    {path: 'home', canActivate: [AuthGuard] ,loadComponent: () => import('./pages/home/home-component/home.component').then(mod => mod.HomeComponent)},
    {path: 'login', canActivate: [TrySilentLogin],loadComponent: () => import('./pages/auth-page/auth-component/auth.component').then(mod => mod.AuthComponent)},
    {path: '**', redirectTo: 'login'}
];
