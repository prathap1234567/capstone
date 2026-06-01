import { Routes } from '@angular/router';
import { Login } from '../login/login';
import { Admin } from '../admin/admin';
import { Register } from '../register/register';
import { UserDashboard } from '../userdashboard/userdashboard';
import { Front } from '../front/front';
import { Updateprof } from '../updateprof/updateprof';
import { Payee } from '../payee/payee';
import { Transfer } from '../transfer/transfer';
import { History } from '../history/history';



export const routes: Routes = [
  {
    path: '',
    component:Front
  },
  {
    path: 'register',
    component:Register
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'admin',
    component: Admin
  },
  
  { 
    path: 'userdashboard',
     component: UserDashboard
    },
    {
  path: 'updateprof',
  component: Updateprof
},
{
    path: 'payees',
    component: Payee
  },
  {
    path: 'transfer',
    component: Transfer
  },
  {
    path: 'history',
    component: History
  }
];